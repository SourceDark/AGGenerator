package org.serc.network.controller.dto;

import java.util.Collections;
import java.util.Date;

import org.serc.algorithm.controller.dto.AbstractDto;
import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.network.model.NetworkScannerSubTask;
import org.serc.network.model.NetworkScannerTask;

public class NetworkScannerTaskDto extends AbstractDto {
    
    private String sensor;
    private String ip;
    private Date startTime;
    private Date endTime;
    private Integer hostCount;
    private Integer vulnerabilitiesCount;
    private Status status;
    
    public NetworkScannerTaskDto() {
        super();
    }
    
    public NetworkScannerTaskDto(NetworkScannerTask task) {
        super();
        sensor = task.getSensor().getName();
        ip = task.getIp();
        Collections.sort(task.getSubTasks());
        startTime = task.getSubTasks().get(0).getStartTime();
        hostCount = task.getSubTasks().size();
        NetworkScannerSubTask lastSubTask = task.getSubTasks().stream().filter(sb -> sb.getStartTime() != null).findFirst().get();
        if(Status.running.equals(lastSubTask.getStatus())) {
            status = Status.running;
        } else {
            endTime = lastSubTask.getEndTime();
            if(task.getSubTasks().stream().filter(sb -> Status.failure.equals(sb.getStatus())).findFirst().isPresent()) {
                status = Status.failure;
            } else {
                status = Status.success;
            }
        }
    }
    public String getSensor() {
        return sensor;
    }
    public void setSensor(String sensor) {
        this.sensor = sensor;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
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

    public Integer getHostCount() {
        return hostCount;
    }

    public void setHostCount(Integer hostCount) {
        this.hostCount = hostCount;
    }

    public Integer getVulnerabilitiesCount() {
        return vulnerabilitiesCount;
    }

    public void setVulnerabilitiesCount(Integer vulnerabilitiesCount) {
        this.vulnerabilitiesCount = vulnerabilitiesCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
