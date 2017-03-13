package org.serc.network.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.serc.model.AbstractEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vul_reports")
public class Host extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;
    
    @Column(name = "host_ip")
    private String ip;
    
    @OneToMany(mappedBy = "host", fetch = FetchType.EAGER)
    private List<HostVulnerability> vulnerabilities;

    @JsonIgnore
    public Sensor getSnesor() {
        return sensor;
    }

    public void setSensor(Sensor snesor) {
        this.sensor = snesor;
    }

    public String getIp() {
        return ip.trim();
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<HostVulnerability> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<HostVulnerability> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }
    
    public String getName() {
        return String.format("%s_%s", sensor.getName(), getIp()).replace(".", "_").replace("-", "_");
    }
    
    public Integer getCveCount() {
        Integer count = 0;
        for(HostVulnerability vulnerability: vulnerabilities) {
            count += vulnerability.getCveCount();
        }
        return count;
    }
}
