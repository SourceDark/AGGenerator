package org.serc.network.controller;

import org.serc.exception.ResourceNotFoundException;
import org.serc.network.model.Host;
import org.serc.network.model.Sensor;
import org.serc.network.support.HostRepository;
import org.serc.network.support.HostVulnerabilityRepository;
import org.serc.network.support.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors/{sensorName}")
public class SensorController {
    
    @Autowired SensorRepository sensorRepository;
    @Autowired HostRepository hostRepository;
    @Autowired HostVulnerabilityRepository hostVulnerabilityRepository;
    
    @ModelAttribute
    public Sensor sensor(@PathVariable String sensorName) {
        Sensor sensor = sensorRepository.findByName(sensorName);
        if(sensor == null) {
            throw new ResourceNotFoundException("sensor not found");
        }
        return sensor;
    }
    
    @GetMapping("")
    public Sensor Sensor(Sensor sensor) {
        return sensor;
    }
    
    @DeleteMapping("")
    public void deleteSensor(Sensor sensor) {
        for(Host host: sensor.getHosts()) {
            hostVulnerabilityRepository.delete(host.getVulnerabilities());
            hostRepository.delete(host);
        }
        sensorRepository.delete(sensor);
    }
    

}
