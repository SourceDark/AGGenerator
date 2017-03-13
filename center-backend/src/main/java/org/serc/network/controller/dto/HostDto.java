package org.serc.network.controller.dto;

import java.util.List;

public class HostDto extends HostListDto {
    
    private List<CveEntry> vulnerabilities;

    public HostDto(String ip, String sensorName) {
        super(ip, sensorName);
    }

    public List<CveEntry> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<CveEntry> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }
    
}
