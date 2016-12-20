package org.serc.network.controller;

import java.util.List;

import org.serc.network.model.Sensor;
import org.serc.network.support.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    
    @Autowired SensorRepository sensorRepository;
    
    @GetMapping("")
    public List<Sensor> sensors() {
        return sensorRepository.findAll();
    }

}
