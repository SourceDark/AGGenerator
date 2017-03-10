package org.serc.network.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.model.AbstractEntity;

@Entity
public class NetworkScannerTask extends AbstractEntity {
    
    @ManyToOne Sensor sensor;
    @Enumerated Status status;
    private String ip;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
