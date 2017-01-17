package org.serc.network.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.serc.algorithm.model.AlgorithmTask;
import org.serc.model.AbstractEntity;

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

}
