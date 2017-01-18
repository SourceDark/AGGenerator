package org.serc.network.model;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.model.AbstractEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.Maps;

@Entity
public class NetworkScheduleTask extends AbstractEntity {
    
    @ManyToOne
    private Network network;
    
    @OneToMany
    @JoinColumn(name = "network_schedule")
    private List<AlgorithmTask> algorithmTasks;

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public List<AlgorithmTask> getAlgorithmTasks() {
        return algorithmTasks;
    }

    public void setAlgorithmTasks(List<AlgorithmTask> algorithmTasks) {
        this.algorithmTasks = algorithmTasks;
    }
    
    private Object getValue(String output, String key) {
        Object results = JSON.parse(output);
        if(!(results instanceof JSONArray)) {
            return null;
        }
        for(Object result: (JSONArray)results) {
            if(key == null || key.equals(JSONPath.eval(result, "$.key").toString())) {
                return JSONPath.eval(result, "$.value");
            }
        }
        return null;
    }
    
    public Map<String, Object> scores() {
        Map<String, Object> scores = Maps.newHashMap();
        Double actualPathCount = 0.0;
        Double potentialPathCount = 0.0;
        Double actualShortestPathCount = 0.0;
        Double potentialShortestPathCount = 0.0;
        for(AlgorithmTask task: getAlgorithmTasks()) {
            if(!task.getStatus().equals(Status.success)) {
                continue;
            }
            if(task.getAlgorithm().getId().equals(11L)) {
                if(task.getInputTask().getAlgorithm().getId().equals(6L)) {
                    Object result = getValue(task.getOutput(), null);
                    if(result != null) {
                        actualPathCount = ((Integer) result).doubleValue();
                    }
                } else {
                    Object result = getValue(task.getOutput(), null);
                    if(result != null) {
                        potentialPathCount = ((Integer) result).doubleValue();
                    }
                }
            } else if(task.getAlgorithm().getId().equals(13L)) {
                if(task.getInputTask().getAlgorithm().getId().equals(6L)) {
                    Object result = getValue(task.getOutput(), null);
                    if(result != null) {
                        actualShortestPathCount = ((Integer) result).doubleValue();
                    }
                } else {
                    Object result = getValue(task.getOutput(), null);
                    if(result != null) {
                        potentialShortestPathCount = ((Integer) result).doubleValue();
                    }
                }
            } else if(task.getAlgorithm().getId().equals(9L)) {
                scores.put("cvssAverage", getValue(task.getOutput(), "cvssAverage"));
            }
        }
        if(actualPathCount == 0.0) {
            actualPathCount = 1.0;
        }
        if(potentialPathCount == 0.0) {
            potentialPathCount = actualPathCount;
        }
        scores.put("attackability", 10 - (actualPathCount / potentialPathCount) * 10);
        if(actualShortestPathCount == 0.0) {
            actualShortestPathCount = 1.0;
        }
        if(potentialShortestPathCount == 0.0) {
            potentialShortestPathCount = actualShortestPathCount;
        }
        scores.put("k-zero", (1 - actualShortestPathCount / potentialShortestPathCount) * 7 + 3);
        scores.put("attack-likehood", 0);
        return scores;
    }

}
