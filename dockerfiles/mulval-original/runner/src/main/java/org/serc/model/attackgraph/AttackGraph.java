package org.serc.model.attackgraph;

import java.util.ArrayList;
import java.util.List;

import org.serc.model.Edge;

public class AttackGraph {
    
    private final List<BaseNode> nodes = new ArrayList<BaseNode>();
    private final List<Edge> edges = new ArrayList<>();
    
    public List<BaseNode> getNodes() {
        return nodes;
    }
    public List<Edge> getEdges() {
        return edges;
    }

}
