package org.serc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vul_reports")
public class Host extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @JsonIgnore
    private Sensor snesor;
    
    @Column(name = "host_ip")
    private String ip;
    
    @OneToMany(mappedBy = "host", fetch = FetchType.EAGER)
    private List<HostVulnerability> vulnerabilities;

    public Sensor getSnesor() {
        return snesor;
    }

    public void setSnesor(Sensor snesor) {
        this.snesor = snesor;
    }

    public String getIp() {
        return ip;
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
}
