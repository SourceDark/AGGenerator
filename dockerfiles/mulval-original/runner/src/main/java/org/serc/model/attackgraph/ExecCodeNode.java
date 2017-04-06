package org.serc.model.attackgraph;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecCodeNode extends PrivilegeNode {
    
    private String privilegeUser;

    public ExecCodeNode(String id, String info, String type, String initial) {
        super(id, info, type, initial);
        Pattern pat = Pattern.compile("execCode\\(([^,]*),([^,]*)\\)");
        Matcher matcher = pat.matcher(info);
        if(matcher.find()) {
            this.host = matcher.group(1);
            this.privilegeUser = matcher.group(2);
        }
    }

    @Override
    public String toString() {
        return "ExecCodeNode " + host + " [privilegeUser=" + privilegeUser + "]";
    }

    @Override
    public String getPrivilegeType() {
        return "execCode";
    }
    
}
