package org.serc.model.attackgraph;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AttackNode extends BaseNode {

    private Service service;
    private Vulnerability vulnerability;
    private String rule;
    
    public AttackNode(String id, String info, String type, String initial) {
        super(id, info, type, initial);
        Pattern pat = Pattern.compile("RULE [0-9]* \\(([^)]*)\\)");
        Matcher matcher = pat.matcher(info);
        if(matcher.find()) {
            rule = matcher.group(1);    
        }
    }
    
    public void setService(String info) {
        Pattern pat = Pattern.compile("networkServiceInfo\\(([^,]*),([^,]*),([^,]*),([^,]*),([^,]*)\\)");
        Matcher matcher = pat.matcher(info);
        if(matcher.find()) {
            service = new Service();
            service.setHost(matcher.group(1));
            service.setName(matcher.group(2));
            service.setPort(matcher.group(4));
            service.setProtocol(matcher.group(3));
            service.setUser(matcher.group(5));
        }
    }
    
    public void setVulnerability(String info) {
        Pattern pat = Pattern.compile("vulExists\\(([^,]*),'([^,]*)',([^,]*),([^,]*),([^,]*)\\)");
        Matcher matcher = pat.matcher(info);
        if(matcher.find()) {
            vulnerability = new Vulnerability();
            vulnerability.setHost(matcher.group(1));
            vulnerability.setCveId(matcher.group(2));
            vulnerability.setService(matcher.group(3));
            vulnerability.setAction(matcher.group(4));
            vulnerability.setResult(matcher.group(5));
            this.host = vulnerability.getHost();
        }
    }
    
    @Override
    public Type getNodeType() {
        return Type.attack;
    }
    
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Vulnerability getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

}
