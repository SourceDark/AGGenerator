package org.serc.network.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.serc.algorithm.controller.dto.AbstractDto;
import org.serc.network.model.NetworkScannerTask;

public class NetworkScannerTaskDto extends AbstractDto {
    
    private String sensor;
    private String ip;
    private List<NetworkScannerSubTaskDto> subTasks;
    
    public NetworkScannerTaskDto() {
        super();
    }
    
    public NetworkScannerTaskDto(NetworkScannerTask task) {
        super();
        sensor = task.getSensor().getName();
        ip = task.getIp();
        subTasks = task.getSubTasks().stream().map(NetworkScannerSubTaskDto::new)
                .collect(Collectors.toList());
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
    public List<NetworkScannerSubTaskDto> getSubTasks() {
        return subTasks;
    }
    public void setSubTasks(List<NetworkScannerSubTaskDto> subTasks) {
        this.subTasks = subTasks;
    }

}
