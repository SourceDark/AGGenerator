package org.serc.network.controller;

import java.util.List;
import java.util.Map;

import org.serc.network.model.Host;
import org.serc.network.model.HostVulnerability;
import org.serc.network.model.Sensor;
import org.serc.network.support.HostRepository;
import org.serc.network.support.HostVulnerabilityRepository;
import org.serc.network.support.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    
    @Autowired SensorRepository sensorRepository;
    @Autowired HostRepository hostRepository;
    @Autowired HostVulnerabilityRepository hostVulnerabilityRepository;
    
    @GetMapping("")
    public List<Sensor> sensors() {
        return sensorRepository.findAll();
    }
    
    @PostMapping
    public void createSensors(@RequestBody String json) {
        Object result = JSON.parse(json);
        String sensorName = JSONPath.eval(result, "$.sensorName").toString();
        String sensorIp = JSONPath.eval(result, "$.sensorName").toString();
        Sensor sensor = sensorRepository.findByName(sensorName);
        if(sensor == null) {
            sensor = new Sensor();
            sensor.setIp(sensorIp);
            sensor.setName(sensorName);
            sensor = sensorRepository.save(sensor);
        }
        JSONObject vulnerabilities = (JSONObject) JSONPath.eval(result, "$.vulnerabilities");
        for(Map.Entry<String, Object> hostEntry: vulnerabilities.entrySet()) {
            String ip = hostEntry.getKey().trim();
            Host host = new Host();
            host.setSensor(sensor);
            host.setIp(ip);
            hostRepository.save(host);
            JSONArray hostVulnerabilities = (JSONArray) hostEntry.getValue();
            for(Object vulnerability: hostVulnerabilities) {
                if(!ip.equals(JSONPath.eval(vulnerability, "$.host").toString().trim())) {
                    continue;
                }
                JSONArray cves = (JSONArray) JSONPath.eval(vulnerability, "$.nvt.cves");
                if(cves == null || cves.isEmpty()) {
                    continue;
                }
                HostVulnerability hostVulnerability = new HostVulnerability();
                hostVulnerability.setCves(cves.toJSONString());
                hostVulnerability.setDescription(JSONPath.eval(vulnerability, "$.description").toString().trim());
                hostVulnerability.setHost(host);
                hostVulnerability.setPortName(JSONPath.eval(vulnerability, "$.port.port_name").toString().trim());
                hostVulnerability.setPortProto(JSONPath.eval(vulnerability, "$.port.number").toString().trim());
                hostVulnerability.setThreat(JSONPath.eval(vulnerability, "$.threat").toString().trim());
                hostVulnerabilityRepository.save(hostVulnerability);
             }
        }
        
    }

}
