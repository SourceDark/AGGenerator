package org.serc.model.attackgraph;

public class Service {
    
    private String host;
    private String name;
    private String protocol;
    private String port;
    private String user;
    
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "Service [host=" + host + ", name=" + name + ", protocol=" + protocol + ", port=" + port + ", user="
                + user + "]";
    }

}
