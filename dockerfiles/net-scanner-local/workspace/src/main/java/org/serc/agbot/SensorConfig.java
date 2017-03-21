package org.serc.agbot;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class SensorConfig {
    
    @Value("${dataDir}")
    private String dataDir;
    
    @Value("${dockerHost}")
    private String dockerHost;
    
    private List<String> ips;
    
    public String getDataDir() {
        return dataDir;
    }
    public List<String> getIps() {
        return ips;
    }
    public String getDockerHost() {
        return dockerHost;
    }
    @Value("${ips}")
    public void setIps(String ips) {
        this.ips = Lists.newArrayList(ips.split(","));
    }

}
