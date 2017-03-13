package org.serc.network.controller.dto;

import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.network.model.NetworkScannerSubTask;
import org.springframework.beans.BeanUtils;

public class NetworkScannerSubTaskDto {
    
    private Long id;
    private Status status;
    private String ip;
    private String containerId;
    private String errorStack;
    public NetworkScannerSubTaskDto() {
        super();
    }
    public NetworkScannerSubTaskDto(NetworkScannerSubTask subTask) {
        super();
        BeanUtils.copyProperties(subTask, this);
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
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
    

}
