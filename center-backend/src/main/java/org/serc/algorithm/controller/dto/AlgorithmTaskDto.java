package org.serc.algorithm.controller.dto;

import org.serc.algorithm.model.AlgorithmTask;

public class AlgorithmTaskDto extends AlgorithmTaskListDto {

    private String input;
    private String output;
    private String errorStack; 
    
    public AlgorithmTaskDto() {
        super();
    }
    public AlgorithmTaskDto(AlgorithmTask task) {
        super(task);
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

}
