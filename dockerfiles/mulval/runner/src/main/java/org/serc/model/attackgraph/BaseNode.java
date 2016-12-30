package org.serc.model.attackgraph;

import org.serc.model.Node;

public abstract class BaseNode extends Node {
    
    public enum Type {
        attacker, privilege, attack;
    }
    
    public BaseNode(String id, String info, String type, String initial) {
        super(id, info, type, initial);
    }
    
    public abstract Type getNodeType();
    
    protected String host;

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }

}
