package org.serc.network.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.model.AbstractEntity;

@Entity
public class NetworkScannerSubTask extends AbstractEntity {
    
    @ManyToOne NetworkScannerTask task;
    @Enumerated Status status;
    private String ip;
    private String containerId;
    private String errorStack;

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

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getErrorStack() {
        return errorStack;
    }

    public void setErrorStack(String errorStack) {
        this.errorStack = errorStack;
    }

    public NetworkScannerTask getTask() {
        return task;
    }

    public void setTask(NetworkScannerTask task) {
        this.task = task;
    }

}
