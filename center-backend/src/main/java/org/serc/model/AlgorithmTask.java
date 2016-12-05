package org.serc.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "algorithm_tasks")
public class AlgorithmTask extends AbstractEntity {
    
    public enum Status {
        running, success, fail, created;
    }
    
    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;
    
    @Lob
    private String input;
    
    @OneToOne
    @JoinColumn(name = "result_id")
    private AlgorithmResult result;
    
    private String containerId;
    
    @Enumerated
    private Status status;
    
    public Algorithm getAlgorithm() {
        return algorithm;
    }
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    public String getInput() {
        return input;
    }
    public void setInput(String input) {
        this.input = input;
    }
    public AlgorithmResult getResult() {
        return result;
    }
    public void setResult(AlgorithmResult result) {
        this.result = result;
    }
    public String getContainerId() {
        return containerId;
    }
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "AlgorithmTask [algorithm=" + algorithm + ", input=" + input + ", result=" + result + ", containerId="
                + containerId + ", status=" + status + "]";
    }

}
