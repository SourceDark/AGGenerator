package org.serc.network.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.serc.algorithm.model.AlgorithmTask.Status;

@Entity
public class NetworkScannerSubTask {
    
    @Id
    @GeneratedValue
    private Long id;
    
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NetworkScannerTask getTask() {
        return task;
    }

    public void setTask(NetworkScannerTask task) {
        this.task = task;
    }

}
