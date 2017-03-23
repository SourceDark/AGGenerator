package org.serc.network.controller.dto;

import java.util.List;

import org.serc.network.model.Host;

public class HostDto extends HostListDto {
    
    private List<CveEntry> vulnerabilities;

    public HostDto(Host host) {
        super(host);
    }

    public List<CveEntry> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<CveEntry> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }
    
}
