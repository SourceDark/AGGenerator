package org.serc.network.controller.dto;

import java.util.Map;

import org.serc.algorithm.controller.dto.AbstractDto;
import org.serc.network.model.Host;
import org.serc.network.model.HostVulnerability;
import org.serc.network.model.Network;
import org.serc.network.model.NetworkScheduleTask;
import org.serc.network.model.Sensor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class NetworkListDto extends AbstractDto {
    
    private String name;
    private Integer sensorCount = 0;
    private Integer hostCount = 0;
    private Integer vulnerabilityCount = 0;
    private Map<String, Object> scores;
    
    public NetworkListDto() {}
    
    public NetworkListDto(Network network) {
        this(network, null);
    }
    
    public NetworkListDto(Network network, NetworkScheduleTask networkScheduleTask) {
        super(network);
        this.sensorCount = network.getSensors().size();
        for(Sensor sensor: network.getSensors()) {
            hostCount += sensor.getHosts().size();
            for(Host host: sensor.getHosts()) {
                for(HostVulnerability hostVulnerability: host.getVulnerabilities()) {
                    Object cves = JSON.parse(hostVulnerability.getCves());
                    if(cves instanceof JSONArray) {
                        vulnerabilityCount += ((JSONArray) cves).size();
                    }
                }
            }
        }
        if(networkScheduleTask != null) {
            scores = networkScheduleTask.scores();
        }
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getSensorCount() {
        return sensorCount;
    }
    public void setSensorCount(Integer sensorCount) {
        this.sensorCount = sensorCount;
    }
    public Integer getHostCount() {
        return hostCount;
    }
    public void setHostCount(Integer hostCount) {
        this.hostCount = hostCount;
    }
    public Integer getVulnerabilityCount() {
        return vulnerabilityCount;
    }
    public void setVulnerabilityCount(Integer vulnerabilityCount) {
        this.vulnerabilityCount = vulnerabilityCount;
    }

    public Map<String, Object> getScores() {
        return scores;
    }

}