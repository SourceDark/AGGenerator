package org.serc.network.support;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.serc.ApplicationContext;
import org.serc.network.controller.dto.CveEntry;
import org.serc.network.model.Host;
import org.serc.network.model.HostVulnerability;
import org.serc.network.model.Network;
import org.serc.network.model.Sensor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

public class NetworkUtils {
    
    public static String getCvesString(Network network) {
        Set<String> cves = Sets.newHashSet();
        for(Sensor sensor: network.getSensors()) {
            for(Host host: sensor.getHosts()) {
                for(HostVulnerability hostVulnerability: host.getVulnerabilities()) {
                    Object cvesJson = JSON.parse(hostVulnerability.getCves());
                    if(cvesJson instanceof JSONArray) {
                        for(Object cveObject: (JSONArray)cvesJson) {
                            cves.add(cveObject.toString());
                            
                        }
                    }
                }
            }
        }
        String cvesString = "";
        for(String cveString: cves) {
            cvesString += cveString + ",";
        }
        return cvesString;
    }
    
    public static String getCvesString(Host host) {
        Set<String> cves = Sets.newHashSet();
        for(HostVulnerability hostVulnerability: host.getVulnerabilities()) {
            Object cvesJson = JSON.parse(hostVulnerability.getCves());
            if(cvesJson instanceof JSONArray) {
                for(Object cveObject: (JSONArray)cvesJson) {
                    cves.add(cveObject.toString());
                    
                }
            }
        }
        String cvesString = "";
        for(String cveString: cves) {
            cvesString += cveString + ",";
        }
        return cvesString;
    }
    
    public static List<CveEntry> getCves(String cvesString) {
        HttpRequest request = HttpRequest.get("http://"+ ApplicationContext.cveApi, ImmutableMap.of("filter", cvesString), false);
        return JSON.parseArray(request.body("utf-8"), CveEntry.class);
    }
    
    public static List<CveEntry> getCves(Network network) {
        return getCves(getCvesString(network));
    }
    
    public static List<CveEntry> getCves(Host host) {
        return getCves(getCvesString(host));
    }
    
    public static CveEntry getCveEntry(List<CveEntry> cveEntries, String id) {
        return cveEntries.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }
    
    public static void setCveEntries(Network network, List<CveEntry> cveEntries) {
        for(Sensor sensor: network.getSensors()) {
            for(Host host: sensor.getHosts()) {
                for(HostVulnerability hostVulnerability: host.getVulnerabilities()) {
                    Object cvesJson = JSON.parse(hostVulnerability.getCves());
                    if(cvesJson instanceof JSONArray) {
                        List<CveEntry> cveList = ((JSONArray)cvesJson).stream().map(c -> getCveEntry(cveEntries, c.toString()))
                                .collect(Collectors.toList());
                        hostVulnerability.setCveList(cveList);
                    } 
                }
            }
        }
    }
    
    public static void setCveEntries(Host host, List<CveEntry> cveEntries) {
        for(HostVulnerability hostVulnerability: host.getVulnerabilities()) {
            Object cvesJson = JSON.parse(hostVulnerability.getCves());
            if(cvesJson instanceof JSONArray) {
                List<CveEntry> cveList = ((JSONArray)cvesJson).stream().map(c -> getCveEntry(cveEntries, c.toString()))
                        .collect(Collectors.toList());
                hostVulnerability.setCveList(cveList);
            } 
        }
    }

}
