package org.serc.controller;

import java.util.List;

public class AlgorithmTaskForm {
    
    private List<String> sensors;
    private String hacls;
    public String getHacls() {
        return hacls;
    }
    public void setHacls(String hacls) {
        this.hacls = hacls;
    }
    public List<String> getSensors() {
        return sensors;
    }
    public void setSensors(List<String> sensors) {
        this.sensors = sensors;
    }

}
