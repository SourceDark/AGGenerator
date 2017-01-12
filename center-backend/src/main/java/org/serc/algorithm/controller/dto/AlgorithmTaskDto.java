package org.serc.algorithm.controller.dto;

import org.serc.algorithm.model.AlgorithmTask;

public class AlgorithmTaskDto extends AlgorithmTaskListDto {

    private String input;
    private String output;
    private String errorStack;
    private Long inputTaskId;
    private Long parentTaskId;
    
    public AlgorithmTaskDto() {
        super();
    }
    public AlgorithmTaskDto(AlgorithmTask task) {
        super(task);
        if(task.getParentTask() != null) {
            this.parentTaskId = task.getParentTask().getId();
        }
        if(task.getInputTask() != null) {
            this.inputTaskId = task.getInputTask().getId();
        }
    }
    
    public String getInput() {
        return input;
    }
    public void setInput(String input) {
        this.input = input;
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
    public Long getParentTaskId() {
        return parentTaskId;
    }
    public void setParentTaskId(Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }
    public Long getInputTaskId() {
        return inputTaskId;
    }
    public void setInputTaskId(Long inputTaskId) {
        this.inputTaskId = inputTaskId;
    }

}
