package org.serc.network.support;

import java.util.List;
import java.util.Map;

import org.serc.network.model.Host;
import org.serc.network.model.HostVulnerability;
import org.serc.network.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

@Service
public class SensorService {
    
    @Autowired SensorRepository sensorRepository;
    @Autowired HostRepository hostRepository;
    @Autowired HostVulnerabilityRepository hostVulnerabilityRepository;
    
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }
    
    public void parseSensor(String json) {
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
        handleVulnerabilities(sensor, vulnerabilities);
    }
    
    public void parseVulnerabilities(Sensor sensor, String json) {
        handleVulnerabilities(sensor, (JSONObject)JSON.parse(json));
    }
    
    private void handleVulnerabilities(Sensor sensor, JSONObject vulnerabilities) {
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
    
    public Sensor findByName(String name) {
        return sensorRepository.findByName(name);
    }

}
