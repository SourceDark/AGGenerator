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

}
