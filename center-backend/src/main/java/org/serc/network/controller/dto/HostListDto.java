package org.serc.network.controller.dto;

import org.serc.network.model.Host;

public class HostListDto implements Comparable<HostListDto> {
    
    private String sensorName;
    private String ip;
    private Double score = 0.0;
    private Integer vulnerabilityCount = 0;
    private Integer value = 3;
    private String nickName;
    private String name;

    public HostListDto(Host host) {
        super();
        this.ip = host.getIp();
        this.sensorName = host.getSensor().getName();
        this.value = host.getValue();
        this.nickName = host.getNickName();
        this.name = host.getName();
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getVulnerabilityCount() {
        return vulnerabilityCount;
    }

    public void setVulnerabilityCount(Integer vulnerabilityCount) {
        this.vulnerabilityCount = vulnerabilityCount;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    @Override
    public int compareTo(HostListDto o) {
        return o.getScore().compareTo(score);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
