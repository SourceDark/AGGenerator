package org.serc.network.controller.dto;

import java.util.Date;

public class CveEntry implements Comparable<CveEntry> {
    
    private String id;
    private Date publishTime;
    private Date lastModifiedTime;
    private String cwe;
    private String softwareList;
    private String description;
    private double cvssScore;
    private String cvssAccessVector;
    private String cvssAccessComplexity;
    private String cvssAuthentication;
    private String cvssConfidentialityImpact;
    private String cvssIntegrityImpact;
    private String cvssAvailabilityImpact;
    private HostListDto host;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Date getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }
    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSoftwareList() {
        return softwareList;
    }
    public void setSoftwareList(String softwareList) {
        this.softwareList = softwareList;
    }
    public String getCwe() {
        return cwe;
    }
    public void setCwe(String cwe) {
        this.cwe = cwe;
    }
    public double getCvssScore() {
        return cvssScore;
    }
    public void setCvssScore(double cvssScore) {
        this.cvssScore = cvssScore;
    }
    public String getCvssAccessVector() {
        return cvssAccessVector;
    }
    public void setCvssAccessVector(String cvssAccessVector) {
        this.cvssAccessVector = cvssAccessVector;
    }
    public String getCvssAccessComplexity() {
        return cvssAccessComplexity;
    }
    public void setCvssAccessComplexity(String cvssAccessComplexity) {
        this.cvssAccessComplexity = cvssAccessComplexity;
    }
    public String getCvssAuthentication() {
        return cvssAuthentication;
    }
    public void setCvssAuthentication(String cvssAuthentication) {
        this.cvssAuthentication = cvssAuthentication;
    }
    public String getCvssConfidentialityImpact() {
        return cvssConfidentialityImpact;
    }
    public void setCvssConfidentialityImpact(String cvssConfidentialityImpact) {
        this.cvssConfidentialityImpact = cvssConfidentialityImpact;
    }
    public String getCvssIntegrityImpact() {
        return cvssIntegrityImpact;
    }
    public void setCvssIntegrityImpact(String cvssIntegrityImpact) {
        this.cvssIntegrityImpact = cvssIntegrityImpact;
    }
    public String getCvssAvailabilityImpact() {
        return cvssAvailabilityImpact;
    }
    public void setCvssAvailabilityImpact(String cvssAvailabilityImpact) {
        this.cvssAvailabilityImpact = cvssAvailabilityImpact;
    }
    @Override
    public int compareTo(CveEntry o) {
        return o.getCvssScore() == cvssScore ? 0 :o.getCvssScore() > cvssScore ? 1 : -1;
    }
    public HostListDto getHost() {
        return host;
    }
    public void setHost(HostListDto host) {
        this.host = host;
    }
    
}
