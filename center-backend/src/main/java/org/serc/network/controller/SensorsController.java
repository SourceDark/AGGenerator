package org.serc.network.controller;

import java.util.List;

import org.serc.network.model.Sensor;
import org.serc.network.support.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    
    @Autowired SensorService sensorService;
    
    @GetMapping("")
    public List<Sensor> sensors() {
        return sensorService.findAll();
    }
    
    @PostMapping
    public void createSensors(@RequestBody String json) {
        sensorService.parseSensor(json);
    }

}
