package org.serc.network.controller.dto;

public class HostListDto implements Comparable<HostListDto> {
    
    private String sensorName;
    private String ip;
    private Double score = 0.0;
    private Integer vulnerabilityCount = 0;

    public HostListDto(String ip, String sensorName) {
        super();
        this.ip = ip;
        this.sensorName = sensorName;
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

}
