package org.serc.model.attackgraph;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.serc.model.Graph;
import org.serc.model.Node;

public class AttackGraphFactory {
    
    private static List<AttackNode> getAttackerNodes(AttackGraph attackGraph, Graph graph, Node node) {
        return graph.getEdges().stream().filter(e -> e.getSource().equals(node.getId()))
        .map(e -> {
            for(BaseNode n : attackGraph.getNodes()) {
                if(e.getTarget().equals(n.getId())) {
                    return (AttackNode)n;
                }
            }
            return null;
        }).filter(n -> n != null).collect(Collectors.toList());
    }
    
    public static AttackGraph toAttackGraph(Graph graph, String hacls) {
        List<ExecCode> goals = getGoals(hacls);
        AttackGraph attackGraph = new AttackGraph();
        for(Node node : graph.getNodes()) {
            if(node.getInfo().startsWith("execCode")) {
                ExecCodeNode execCodeNode = new ExecCodeNode(node.getId(), node.getInfo(), node.getType(), node.getInitial());
                if(isGoal(goals, node.getInfo())) {
                    execCodeNode.setGoal(true);
                }
                attackGraph.getNodes().add(execCodeNode);
            } else if (node.getInfo().startsWith("RULE")) {
                attackGraph.getNodes().add(new AttackNode(node.getId(), node.getInfo(), node.getType(), node.getInitial()));
            }else if (node.getInfo().startsWith("netAccess")) {
                attackGraph.getNodes().add(new NetAccessNode(node.getId(), node.getInfo(), node.getType(), node.getInitial()));
            }else if (node.getInfo().startsWith("attackerLocated")) {
                attackGraph.getNodes().add(new AttackerNode(node.getId(), node.getInfo(), node.getType(), node.getInitial()));
            }
        }
        for(Node node : graph.getNodes()) {
            if(node.getInfo().startsWith("networkServiceInfo")) {
                for(AttackNode attackerNode: getAttackerNodes(attackGraph, graph, node)) {
                    attackerNode.setService(node.getInfo());
                }
            }
            if(node.getInfo().startsWith("vulExists")) {
                for(AttackNode attackerNode: getAttackerNodes(attackGraph, graph, node)) {
                    attackerNode.setVulnerability(node.getInfo());
                }
            }
        }
        attackGraph.getEdges().addAll(graph.getEdges().stream().filter(e -> {
            boolean sourceExist = false;
            boolean targetExist = false;
            for(BaseNode node : attackGraph.getNodes()) {
                if(node.getId().equals(e.getSource())) {
                    sourceExist = true;
                }
                if(node.getId().equals(e.getSource())) {
                    targetExist = true;
                }
            }
            return sourceExist && targetExist;
        }).collect(Collectors.toList()));
        return attackGraph;
    }
    
    private static boolean isGoal(List<ExecCode> goals, String info) {
        ExecCode execCode = new ExecCode(info);
        for(ExecCode goal: goals) {
            if(!goal.getHost().equals(execCode.getHost())) {
                continue;
            }
            if(goal.getUser().equals("_") || goal.getUser().equals(execCode.getUser())){
                return true;
            }
        }
        return false;
    }
    
    private static List<ExecCode> getGoals(String hacls) {
        List<ExecCode> goals = new ArrayList<>(); 
        Pattern pat = Pattern.compile("attackGoal\\((.*)\\)\\.");
        Matcher matcher = pat.matcher(hacls);
        while (matcher.find()) {
            String target = matcher.group(1);
            goals.add(new ExecCode(target.trim()));
        }
        return goals;
    }
    
}
