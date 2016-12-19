package org.serc.workbench.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.serc.model.AbstractEntity;

@Entity
public class Hacl extends AbstractEntity {
    
    @ManyToOne
    @JoinColumn(name = "`from`")
    private Host from;
    
    @ManyToOne
    @JoinColumn(name = "`to`")
    private Host to;
    
    private String protocol;
    private String port;
    
    public Host getFrom() {
        return from;
    }
    public void setFrom(Host from) {
        this.from = from;
    }
    public Host getTo() {
        return to;
    }
    public void setTo(Host to) {
        this.to = to;
    }
    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }

}
