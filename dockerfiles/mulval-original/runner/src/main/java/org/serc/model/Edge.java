package org.serc.model;

public class Edge {
    
    private String source;
    private String target;
    public Edge(String source, String target) {
        super();
        this.source = source;
        this.target = target;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    
}
