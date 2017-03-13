package org.serc.network.controller.dto;

import org.serc.network.model.Sensor;
import org.springframework.beans.BeanUtils;

public class SensorListDto {
    
    private String name;
    private String ip;
    private String api;
    public SensorListDto() {
        super();
    }
    public SensorListDto(Sensor sensor) {
        BeanUtils.copyProperties(sensor, this);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getApi() {
        return api;
    }
    public void setApi(String api) {
        this.api = api;
    }

}
