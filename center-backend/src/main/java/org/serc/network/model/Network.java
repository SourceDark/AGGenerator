package org.serc.network.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import org.serc.model.AbstractEntity;

@Entity
public class Network extends AbstractEntity {
    
    @ManyToMany
    @JoinTable(name = "network_sensors", joinColumns = @JoinColumn(name = "network", referencedColumnName = "id"), 
            uniqueConstraints = @UniqueConstraint(columnNames = { "network", "sensor" }), 
            inverseJoinColumns = @JoinColumn(name = "sensor", referencedColumnName = "id"))
    private List<Sensor> sensors;
    
    private String name;
    private String hacls;
    private Double score;
    private Integer sensorCount;
    private Integer hostCount;
    private Integer dangerVulnerabilityCount;
    private Integer vulnerabilityCount;
    private Integer dangerHostCount;
    
    public List<Sensor> getSensors() {
        return sensors;
    }
    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
    public String getHacls() {
        return hacls;
    }
    public void setHacls(String hacls) {
        this.hacls = hacls;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public Integer getDangerHostCount() {
        return dangerHostCount;
    }
    public void setDangerHostCount(Integer dangerHostCount) {
        this.dangerHostCount = dangerHostCount;
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
}
