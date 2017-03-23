package org.serc.server.model;

import java.util.Date;
import java.util.List;

import jersey.repackaged.com.google.common.collect.Lists;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Host {
    
    private String inner_interface;
    private String outer_interface;
    private String gateway;
    private List<Service> services;
    private String mac;
    private String name;
    private Date updated;
    private String sensorName;
    private Integer value;
    private Integer vulnerabilityCount;
    private Double score = 0d;
    
    @JsonIgnore
    private List<Host> subGateWays = Lists.newArrayList();
    @JsonIgnore
    private List<Host> subHosts = Lists.newArrayList();
    private Integer gateWayCount = 0;
    private Integer importantHostCount = 0;
    
    public String getInner_interface() {
        return inner_interface;
    }
    public void setInner_interface(String inner_interface) {
        this.inner_interface = inner_interface;
    }
    public String getOuter_interface() {
        return outer_interface == null ? "": outer_interface.trim();
    }
    public void setOuter_interface(String outer_interface) {
        this.outer_interface = outer_interface;
    }
    public String getGateway() {
        return gateway;
    }
    public void setGateway(String gateway) {
        this.gateway = gateway;
    }
    public List<Service> getServices() {
        return services;
    }
    public void setServices(List<Service> services) {
        this.services = services;
    }
    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    public String getSensorName() {
        return sensorName;
    }
    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
    public Integer getValue() {
        return value == null ? 3: value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    public Integer getVulnerabilityCount() {
        return vulnerabilityCount;
    }
    public void setVulnerabilityCount(Integer vulnerabilityCount) {
        this.vulnerabilityCount = vulnerabilityCount;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }
    public List<Host> getSubGateWays() {
        return subGateWays;
    }
    public void setSubGateWays(List<Host> subGateWays) {
        this.subGateWays = subGateWays;
    }
    public Integer getGateWayCount() {
        return gateWayCount;
    }
    public void setGateWayCount(Integer gateWayCount) {
        this.gateWayCount = gateWayCount;
    }
    public Integer getImportantHostCount() {
        Integer importantHostCount = this.importantHostCount;
        for(Host host: subGateWays) {
            importantHostCount += host.getImportantHostCount();
        }
        return importantHostCount;
    }
    public void setImportantHostCount(Integer importantHostCount) {
        this.importantHostCount = importantHostCount;
    }
    public List<Host> getSubHosts() {
        return subHosts;
    }
    public void setSubHosts(List<Host> subHosts) {
        this.subHosts = subHosts;
    }
    public Boolean isGateWay() {
        return !getOuter_interface().trim().isEmpty();
    }
    @JsonIgnore
    public Double getTotalLoss() {
        if(!isGateWay()) {
            return score;
        }
        Double totalLoss = 0d;
        for(Host host: subGateWays) {
            totalLoss += host.getTotalLoss();
        }
        for(Host host: subHosts) {
            totalLoss += host.getTotalLoss();
        }
        return totalLoss;
    }
    @JsonIgnore
    public Double getTotalValue() {
        if(!isGateWay()) {
            return (double) getValue();
        }
        Double totalValue = 0d;
        for(Host host: subGateWays) {
            totalValue += host.getTotalValue();
        }
        for(Host host: subHosts) {
            totalValue += host.getTotalValue();
        }
        return totalValue;
    }
}
