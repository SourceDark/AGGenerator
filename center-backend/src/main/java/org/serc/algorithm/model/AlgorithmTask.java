package org.serc.algorithm.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.serc.model.AbstractEntity;

@Entity
@Table(name = "algorithm_tasks")
public class AlgorithmTask extends AbstractEntity {

    public enum Status {
        running, success, failure, created;
    }
    
    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;
    
    @ManyToOne(optional = true)
    private AlgorithmTask inputTask;
    
    @Lob String input;
    @Lob String output;
    @Lob String errorStack; // optional, then reason why status is failure
    @Enumerated Status status;
    @ManyToOne(optional = true) ResultType inputType;
    @ManyToOne(optional = true) ResultType outputType;
    String containerId;
    
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
    public String getOutput() {
        return output;
    }
    public void setOutput(String output) {
        this.output = output;
    }
    public String getErrorStack() {
        return errorStack;
    }
    public void setErrorStack(String errorStack) {
        this.errorStack = errorStack;
    }
    
    public AlgorithmTask getInputTask() {
        return inputTask;
    }
    
    public void setInputTask(AlgorithmTask inputTask) {
        this.inputTask = inputTask;
    }
    public ResultType getInputType() {
        return inputType;
    }
    public void setInputType(ResultType inputType) {
        this.inputType = inputType;
    }
    public ResultType getOutputType() {
        return outputType;
    }
    public void setOutputType(ResultType outputType) {
        this.outputType = outputType;
    }
    @Override
    public String toString() {
        return "AlgorithmTask [algorithm=" + algorithm + ", input=" + input + ", containerId="
                + containerId + ", status=" + status + "]";
    }

}
