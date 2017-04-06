package org.serc.model;

import java.util.List;

import com.google.common.collect.Lists;

public class Graph {
    
    private List<Node> nodes;
    private List<Edge> edges;
    public Graph() {
        super();
        nodes = Lists.newArrayList();
        edges = Lists.newArrayList();
    }
    public List<Node> getNodes() {
        return nodes;
    }
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
    public List<Edge> getEdges() {
        return edges;
    }
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

}
