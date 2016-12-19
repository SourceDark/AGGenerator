package org.serc.workbench.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.serc.model.AbstractEntity;
import org.springframework.context.annotation.Configuration;

@Entity
@Table(name = "sensors")
@Configuration
public class Sensor extends AbstractEntity {
    
    private String name;
    private String ip;
    
    @OneToMany(mappedBy = "sensor", fetch = FetchType.EAGER)
    private List<Host> hosts;
    
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
    public List<Host> getHosts() {
        return hosts;
    }
    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }
}
