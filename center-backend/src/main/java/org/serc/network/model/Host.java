package org.serc.network.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jersey.repackaged.com.google.common.collect.Lists;

import org.serc.model.AbstractEntity;
import org.serc.network.controller.dto.CveEntry;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vul_reports")
public class Host extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;
    
    @Column(name = "host_ip")
    private String ip;
    
    @Column(name = "`value`")
    private Integer value;
    
    @Column(name = "nickName")
    private String nickName;
    
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
    
    public Double getScore() {
        List<Double>  possibilities = Lists.newArrayList();
        for(HostVulnerability vulnerability: vulnerabilities) {
            for(CveEntry cveEntry: vulnerability.getCveList()) {
                possibilities.add(cveEntry.getCvssScore() / 10);
            }
        }
        Double totalPossibility = 1d;
        for(Double possiblity: possibilities) {
            totalPossibility *= possiblity;
        }
        return getValue() * (1 - totalPossibility);
    }

    public Integer getValue() {
        return value == null ? 3 : value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getNickName() {
        return nickName == null ? getName() : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Sensor getSensor() {
        return sensor;
    }
}
