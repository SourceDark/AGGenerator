package org.serc.server;

import java.util.List;
import java.util.stream.Collectors;

import org.serc.exception.ResourceNotFoundException;
import org.serc.network.controller.dto.CveEntry;
import org.serc.network.controller.dto.HostDto;
import org.serc.network.controller.dto.SensorListDto;
import org.serc.network.model.Host;
import org.serc.network.model.HostVulnerability;
import org.serc.network.model.Network;
import org.serc.network.support.NetworkUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RestController("ServerSensorController")
@RequestMapping("/server/{network}/sensors")
public class SensorController {
    
    @GetMapping("")
    public List<SensorListDto> getSensors(Network network) {
        return network.getSensors().stream().map(SensorListDto::new).collect(Collectors.toList());
    }
    
    @GetMapping("/{sensorName}/hosts/{ip:[0-9\\.]*}")
    public HostDto host(Network network, @PathVariable String sensorName, @PathVariable String ip) {
        Host host = network.getSensors().stream().filter(s -> s.getName().equals(sensorName)).findFirst()
                .orElseThrow(ResourceNotFoundException::new)
                .getHosts().stream().filter(h -> h.getIp().trim().equals(ip)).findFirst()
                .orElseThrow(ResourceNotFoundException::new);
        List<CveEntry> cves = Lists.newArrayList();
        HostDto hostDto = new HostDto(host.getIp(), sensorName);
        NetworkUtils.setCveEntries(host, NetworkUtils.getCves(host));
        for(HostVulnerability hostVulnerability: host.getVulnerabilities()) {
            for(CveEntry vulnerability: hostVulnerability.getCveList()) {
                hostDto.setScore(hostDto.getScore() + vulnerability.getCvssScore());
                hostDto.setVulnerabilityCount(hostDto.getVulnerabilityCount() + 1);
                cves.add(vulnerability);
            }
        }
        hostDto.setVulnerabilities(cves);
        return hostDto;
    }

}
