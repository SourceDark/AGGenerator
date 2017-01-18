package org.serc.algorithm.controller.dto;

import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.model.AlgorithmTask.Status;
import org.springframework.beans.BeanUtils;

public class AlgorithmTaskListDto extends AbstractDto {

    private Status status;
    private String containerId;
    private SimpleDto algorithm;
    private String inputType;
    private String outputType;
    
    public AlgorithmTaskListDto() {
        super();
    }
    public AlgorithmTaskListDto(AlgorithmTask task) {
        super();
        BeanUtils.copyProperties(task, this);
        algorithm = new SimpleDto(task.getAlgorithm());
        inputType = task.getInputType().getName();
        outputType = task.getOutputType().getName();
    }
    
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getContainerId() {
        return containerId;
    }
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
    public SimpleDto getAlgorithm() {
        return algorithm;
    }
    public void setAlgorithm(SimpleDto algorithm) {
        this.algorithm = algorithm;
    }
    public String getInputType() {
        return inputType;
    }
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
    public String getOutputType() {
        return outputType;
    }
    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

}
