package org.serc.server;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.serc.network.controller.dto.CveEntry;
import org.serc.network.controller.dto.HostListDto;
import org.serc.network.controller.dto.NetworkListDto;
import org.serc.network.model.Host;
import org.serc.network.model.HostVulnerability;
import org.serc.network.model.Network;
import org.serc.network.model.NetworkScheduleTask;
import org.serc.network.model.Sensor;
import org.serc.network.support.NetworkScheduleTaskRepository;
import org.serc.network.support.NetworkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RestController
@RequestMapping("/server/{network}")
public class ServerController {
    
    @Autowired NetworkScheduleTaskRepository networkScheduleTaskRepository;
    
    @GetMapping("/overview")
    public NetworkListDto network(Network network) {
        return new NetworkListDto(network);
    }
    
    @GetMapping("/scores")
    public Map<Long, Map<String, Object>> scores(Network network) {
        return networkScheduleTaskRepository.findByNetworkOrderByIdDesc(network).stream()
                .collect(Collectors.toMap(n -> n.getCreatedTime().getTime(), NetworkScheduleTask::scores));
    }
    
    @GetMapping("/vulnerabilities")
    public List<CveEntry> vulnerabilities(Network network) {
        List<CveEntry> cves = Lists.newArrayList();
        NetworkUtils.setCveEntries(network, NetworkUtils.getCves(network));
        for(Sensor sensor: network.getSensors()) {
            for(Host host: sensor.getHosts()) {
                HostListDto hostDto = new HostListDto(host.getIp(), sensor.getName());
                for(HostVulnerability hostVulnerability: host.getVulnerabilities()) {
                    for(CveEntry vulnerability: hostVulnerability.getCveList()) {
                        hostDto.setScore(hostDto.getScore() + vulnerability.getCvssScore());
                        hostDto.setVulnerabilityCount(hostDto.getVulnerabilityCount() + 1);
                        vulnerability.setHost(hostDto);
                        cves.add(vulnerability);
                    }
                }
            }
        }
        Collections.sort(cves);
        return cves;
    }
    
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
    
}