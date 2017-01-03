package org.serc.agbot;

import java.util.List;

import org.serc.model.Edge;
import org.serc.model.Graph;
import org.serc.model.Node;

import com.google.common.collect.Lists;

public class Mulval2Json {
    
    public static Graph run(List<String> lines, String hacls) {
        Graph graph = new Graph();
        for(String nodeOrEdge : lines) {
            if(nodeOrEdge.trim() == "") {
                continue;
            }
            if(nodeOrEdge.split(",").length > 3) {
                List<String> nodeStrings = Lists.newArrayList(nodeOrEdge);
                graph.getNodes().add(new Node(getAttribute(nodeStrings), getAttribute(nodeStrings), 
                        getAttribute(nodeStrings), getAttribute(nodeStrings)));
            } else {
                String[] edge = nodeOrEdge.split(",");
                graph.getEdges().add(new Edge(edge[1], edge[0]));
            }
        }
        return graph;
    }
    
    private static String getAttribute(List<String> nodeStrings) {
        String nodeString = nodeStrings.get(0);
        String attribute;
        if (nodeString.split(",").length == 1) {
            attribute = nodeString;
            nodeString = "";
        } else if(nodeString.startsWith("\"")){
            attribute = nodeString.substring(1, nodeString.indexOf("\","));
            nodeString = nodeString.substring(nodeString.indexOf("\",") + 2);
        } else if(nodeString.startsWith("\'")) {
            attribute = nodeString.substring(1, nodeString.indexOf("',"));
            nodeString = nodeString.substring(nodeString.indexOf("',") + 2);
        } else {
            attribute = nodeString.substring(0, nodeString.indexOf(","));
            nodeString = nodeString.substring(nodeString.indexOf(",") + 1);
        }
        nodeStrings.set(0, nodeString);
        return attribute;
    }
    
}
