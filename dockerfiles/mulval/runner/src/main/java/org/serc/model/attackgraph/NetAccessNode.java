package org.serc.model.attackgraph;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetAccessNode extends PrivilegeNode {
    
    private String protocol;
    private String port;

    public NetAccessNode(String id, String info, String type, String initial) {
        super(id, info, type, initial);
        Pattern pat = Pattern.compile("netAccess\\(([^,]*),([^,]*),([^,]*)\\)");
        Matcher matcher = pat.matcher(info);
        if(matcher.find()) {
            this.host = matcher.group(1);
            this.protocol = matcher.group(2);
            this.port = matcher.group(3);
        }
    }

    @Override
    public String getPrivilegeType() {
        return "netAccess";
    }
    
    @Override
    public String toString() {
        return "NetAccessNode " + host + "[protocol=" + protocol + ", port=" + port + "]";
    }

}
