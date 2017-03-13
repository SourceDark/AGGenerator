package org.serc.network.controller.dto;

import java.util.Map;

import org.serc.algorithm.controller.dto.AbstractDto;
import org.serc.network.model.Network;
import org.serc.network.model.NetworkScheduleTask;
import org.springframework.beans.BeanUtils;

public class NetworkListDto extends AbstractDto {
    
    private String name;
    private Map<String, Object> scores;
    private Double score;
    private Integer sensorCount;
    private Integer hostCount;
    private Integer dangerVulnerabilityCount;
    private Integer vulnerabilityCount;
    private Integer dangerHostCount;
    
    
    public NetworkListDto() {}
    
    public NetworkListDto(Network network) {
        this(network, null);
    }
    
    public NetworkListDto(Network network, NetworkScheduleTask networkScheduleTask) {
        BeanUtils.copyProperties(network, this, "sensors");
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
    public Map<String, Object> getScores() {
        return scores;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
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
    public Integer getDangerVulnerabilityCount() {
        return dangerVulnerabilityCount;
    }
    public void setDangerVulnerabilityCount(Integer dangerVulnerabilityCount) {
        this.dangerVulnerabilityCount = dangerVulnerabilityCount;
    }
    public Integer getVulnerabilityCount() {
        return vulnerabilityCount;
    }
    public void setVulnerabilityCount(Integer vulnerabilityCount) {
        this.vulnerabilityCount = vulnerabilityCount;
    }
    public Integer getDangerHostCount() {
        return dangerHostCount;
    }
    public void setDangerHostCount(Integer dangerHostCount) {
        this.dangerHostCount = dangerHostCount;
    }
    public void setScores(Map<String, Object> scores) {
        this.scores = scores;
    }


}
