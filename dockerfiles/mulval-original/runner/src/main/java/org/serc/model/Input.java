package org.serc.model;

import java.util.List;

public class Input {
    
    private List<Sensor> sensors;
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

}
