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
    
    public Map<String, Object> scores() {
        Map<String, Object> scores = Maps.newHashMap();
        for(AlgorithmTask task: getAlgorithmTasks()) {
            if(task.getStatus().equals(Status.success)) {
                Object results = JSON.parse(task.getOutput());
                if(!(results instanceof JSONArray)) {
                    continue;
                }
                for(Object result: (JSONArray)results) {
                    scores.put(JSONPath.eval(result, "$.key").toString(), JSONPath.eval(result, "$.value").toString());
                }
            }
        }
        return scores;
    }

}
