package org.serc.network.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.serc.model.AbstractEntity;
import org.serc.network.model.NetworkScheduleTask.Status;

@Entity
public class NetworkScannerSubTask extends AbstractEntity implements Comparable<NetworkScannerSubTask> {
    
    @ManyToOne NetworkScannerTask task;
    @Enumerated Status status;
    private String ip;
    private String containerId;
    private String errorStack;
    private Date startTime;
    private Date endTime;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public int compareTo(NetworkScannerSubTask o) {
        if(startTime == null) {
            return 1;
        }
        return startTime.compareTo(o.getStartTime());
    }

}
