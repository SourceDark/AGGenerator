package org.serc.server.support;

import java.util.List;
import java.util.stream.Collectors;

import org.serc.ApplicationContext;
import org.serc.network.model.Sensor;
import org.serc.network.support.NetworkUtils;
import org.serc.network.support.SensorService;
import org.serc.server.model.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;

@Component
public class HostService {
    
    @Autowired
    private SensorService sensorService;
    
    public List<Host> hosts() {
        HttpRequest request = HttpRequest.get(ApplicationContext.networkApi + "/hosts");
        List<Host> hosts =  JSON.parseArray(request.body("utf-8"), Host.class);
        Sensor sensor = sensorService.findByName("xr-test");
        for(Host host: hosts) {
            host.setSensorName("xr-test");
            org.serc.network.model.Host hostWithVul = sensor.getHosts().stream()
                    .filter(h -> h.getIp().trim().equals(host.getInner_interface()))
                    .findFirst().orElse(null);
            if(hostWithVul == null) {
                continue;
            }
            NetworkUtils.setCveEntries(hostWithVul, NetworkUtils.getCves(hostWithVul));
            host.setValue(hostWithVul.getValue());
            host.setVulnerabilityCount(hostWithVul.getCveCount());
            host.setScore(hostWithVul.getScore());
        }
        List<Host> gateWays = hosts.stream().filter(h -> h.isGateWay()).collect(Collectors.toList());
        
        for(Host gateWay: gateWays) {
            List<Host> subGateWays = hosts.stream().filter(h -> h.isGateWay()
                    && gateWay.getInner_interface().equals(h.getGateway())
                    && !gateWay.getInner_interface().equals(h.getInner_interface())).collect(Collectors.toList());
            List<Host> subHosts = hosts.stream().filter(h -> !h.isGateWay()
                    && gateWay.getInner_interface().equals(h.getGateway())).collect(Collectors.toList());
            gateWay.setSubGateWays(subGateWays);
            gateWay.setSubHosts(subHosts);
            gateWay.setGateWayCount(subGateWays.size());
            gateWay.setImportantHostCount((int)subHosts.stream().filter(h -> h.getValue() > 7).count());
        }
        for(Host gateWay: gateWays) {
            gateWay.setScore((gateWay.getTotalLoss() / gateWay.getTotalValue()) * 100);
        }
        return hosts;
    }

}
