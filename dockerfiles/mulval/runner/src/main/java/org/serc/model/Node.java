package org.serc.model;

public class Node {
    
    private String id;
    private String info;
    private String type;
    private String initial;
    
    public Node(String id, String info, String type, String initial) {
        super();
        this.id = id;
        this.info = info;
        this.type = type;
        this.initial = initial;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getInitial() {
        return initial;
    }
    public void setInitial(String initial) {
        this.initial = initial;
    }
}
