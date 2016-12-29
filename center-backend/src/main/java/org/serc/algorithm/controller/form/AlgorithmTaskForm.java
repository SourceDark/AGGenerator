package org.serc.algorithm.controller.form;

import javax.validation.constraints.NotNull;

public class AlgorithmTaskForm {
    
    @NotNull
    private String algorithm;
    
    @NotNull
    private String input;
    
    private Long parentTask;

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Long getParentTask() {
        return parentTask;
    }

    public void setParentTask(Long parentTask) {
        this.parentTask = parentTask;
    }

}
