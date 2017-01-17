package org.serc.model.attackgraph;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttackerNode extends BaseNode {
    
    public AttackerNode(String id, String info, String type, String initial) {
        super(id, info, type, initial);
        Pattern pat = Pattern.compile("attackerLocated\\(([^\\)]*)");
        Matcher matcher = pat.matcher(info);
        if(matcher.find()) {
            host = matcher.group(1);    
        }
        
    }

    @Override
    public Type getNodeType() {
        return Type.attacker;
    }
    
}
