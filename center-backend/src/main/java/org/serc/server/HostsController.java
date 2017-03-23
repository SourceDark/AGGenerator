package org.serc.server;

import java.util.Collections;
import java.util.List;

import org.serc.network.controller.dto.CveEntry;
import org.serc.network.controller.dto.HostListDto;
import org.serc.network.model.Host;
import org.serc.network.model.HostVulnerability;
import org.serc.network.model.Network;
import org.serc.network.model.Sensor;
import org.serc.network.support.NetworkUtils;
import org.serc.server.support.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RestController
@RequestMapping("/server/{network}")
public class HostsController {
    
    @Autowired
    private HostService hostService;
    
    @GetMapping("/hosts")
    public List<HostListDto> hosts(Network network) {
        List<HostListDto> hosts = Lists.newArrayList();
        NetworkUtils.setCveEntries(network, NetworkUtils.getCves(network));
        for(Sensor sensor: network.getSensors()) {
            for(Host host: sensor.getHosts()) {
                HostListDto hostDto = new HostListDto(host.getIp(), sensor.getName());
                hosts.add(hostDto);
                for(HostVulnerability hostVulnerability: host.getVulnerabilities()) {
                    for(CveEntry vulnerability: hostVulnerability.getCveList()) {
                        hostDto.setScore(hostDto.getScore() + vulnerability.getCvssScore());
                        hostDto.setVulnerabilityCount(hostDto.getVulnerabilityCount() + 1);
                    }
                }
            }
        }
        Collections.sort(hosts);
        return hosts;
    }
    
    @GetMapping("/hosts/network")
    public List<org.serc.server.model.Host> network() {
        return hostService.hosts();
    }

}
