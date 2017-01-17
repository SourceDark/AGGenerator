package org.serc.network.controller.dto;

import java.util.Map;

import org.serc.network.model.Host;
import org.serc.network.model.HostVulnerability;
import org.serc.network.model.Network;
import org.serc.network.model.Sensor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;

public class NetworkListDto {
    
    private String name;
    private Integer sensorCount = 0;
    private Integer hostCount = 0;
    private Integer vulnerabilityCount = 0;
    private final Map<String, Object> scores = Maps.newHashMap();
    
    public NetworkListDto() {}
    
    public NetworkListDto(Network network) {
        this.name = network.getName();
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
