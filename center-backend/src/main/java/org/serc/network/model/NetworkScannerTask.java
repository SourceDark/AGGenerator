package org.serc.network.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.serc.model.AbstractEntity;

@Entity
public class NetworkScannerTask extends AbstractEntity {
    
    @ManyToOne Sensor sensor;
    private String ip;
    
    @OneToMany(mappedBy = "task")
    private List<NetworkScannerSubTask> subTasks;

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<NetworkScannerSubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<NetworkScannerSubTask> subTasks) {
        this.subTasks = subTasks;
    }

}
