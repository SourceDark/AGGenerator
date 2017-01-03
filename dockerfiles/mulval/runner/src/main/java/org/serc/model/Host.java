package org.serc.model;

import java.util.List;

public class Host extends AbstractEntity {

    private Sensor snesor;
    private String ip;
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
