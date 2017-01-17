package org.serc.network.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.serc.ApplicationContext;
import org.serc.network.controller.dto.CveEntry;
import org.serc.network.controller.dto.NetworkListDto;
import org.serc.network.model.Host;
import org.serc.network.model.HostVulnerability;
import org.serc.network.model.Network;
import org.serc.network.model.NetworkScheduleTask;
import org.serc.network.model.Sensor;
import org.serc.network.support.NetworkRepository;
import org.serc.network.support.NetworkScheduleService;
import org.serc.network.support.NetworkScheduleTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

@RestController
@RequestMapping("/networks")
public class NetworkController {
    
    @Autowired NetworkRepository networkRepository;
    @Autowired NetworkScheduleService networkScheduleService;
    @Autowired NetworkScheduleTaskRepository networkScheduleTaskRepository;
    
    @GetMapping("")
    public List<NetworkListDto> networks() {
        return networkRepository.findAll().stream()
                .map(network -> new NetworkListDto(network, networkScheduleTaskRepository.findTopByNetworkOrderByIdDesc(network)))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/schedule")
    public void schedule() throws Exception {
        networkScheduleService.run();
    }
    
    @GetMapping("/{network}")
    public NetworkListDto network(Network network) {
        return new NetworkListDto(network);
    }
    
    @GetMapping("/{network}/scores")
    public Map<Long, Map<String, Object>> scores(Network network) {
        return networkScheduleTaskRepository.findByNetworkOrderByIdDesc(network).stream()
                .collect(Collectors.toMap(n -> n.getCreatedTime().getTime(), NetworkScheduleTask::scores));
    }
    
    private String getCvesString(Network network) {
        Set<String> cves = Sets.newHashSet();
        for(Sensor sensor: network.getSensors()) {
            for(Host host: sensor.getHosts()) {
                for(HostVulnerability hostVulnerability: host.getVulnerabilities()) {
                    Object cvesJson = JSON.parse(hostVulnerability.getCves());
                    if(cvesJson instanceof JSONArray) {
                        for(Object cveObject: (JSONArray)cvesJson) {
                            cves.add(cveObject.toString());
                            
                        }
                    }
                }
            }
        }
        String cvesString = "";
        for(String cveString: cves) {
            cvesString += cveString + ",";
        }
        return cvesString;
    }
    
    private CveEntry getCveEntry(List<CveEntry> cveEntries, String id) {
        return cveEntries.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }
    
    private void setCveEntries(Network network, List<CveEntry> cveEntries) {
        for(Sensor sensor: network.getSensors()) {
            for(Host host: sensor.getHosts()) {
                for(HostVulnerability hostVulnerability: host.getVulnerabilities()) {
                    Object cvesJson = JSON.parse(hostVulnerability.getCves());
                    if(cvesJson instanceof JSONArray) {
                        List<CveEntry> cveList = ((JSONArray)cvesJson).stream().map(c -> getCveEntry(cveEntries, c.toString()))
                                .collect(Collectors.toList());
                        hostVulnerability.setCveList(cveList);
                    }
                }
            }
        }
    }
    
    @GetMapping("/{network}/sensors")
    public List<Sensor> vulnerabilities(Network network) {
        String cvesString = getCvesString(network);
        HttpRequest request = HttpRequest.get("http://"+ ApplicationContext.cveApi, ImmutableMap.of("filter", cvesString), false);
        List<CveEntry> cveEntries = JSON.parseArray(request.body("utf-8"), CveEntry.class);
        setCveEntries(network, cveEntries);
        return network.getSensors();
    }

}
