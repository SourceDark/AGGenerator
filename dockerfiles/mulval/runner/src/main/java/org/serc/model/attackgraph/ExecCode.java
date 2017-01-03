package org.serc.model.attackgraph;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecCode {
    
    public String host;
    public String user;

    public ExecCode(String info) {
        Pattern pat = Pattern.compile("execCode\\(([^,]*),([^,]*)\\)");
        Matcher matcher = pat.matcher(info);
        while (matcher.find()) {
            host = matcher.group(1);
            user = matcher.group(2);
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ExecCode [host=" + host + ", user=" + user + "]";
    }

}
