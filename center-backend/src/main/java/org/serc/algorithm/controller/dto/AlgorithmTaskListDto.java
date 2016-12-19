package org.serc.algorithm.controller.dto;

import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.model.AlgorithmTask.Status;
import org.springframework.beans.BeanUtils;

public class AlgorithmTaskListDto extends AbstractDto {

    private Status status;
    private String containerId;
    private SimpleDto algorithm;
    
    public AlgorithmTaskListDto() {
        super();
    }
    public AlgorithmTaskListDto(AlgorithmTask task) {
        super();
        BeanUtils.copyProperties(task, this);
        algorithm = new SimpleDto(task.getAlgorithm());
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

}
