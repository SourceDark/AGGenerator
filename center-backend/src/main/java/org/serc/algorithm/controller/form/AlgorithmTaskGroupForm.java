package org.serc.algorithm.controller.form;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.serc.algorithm.model.AlgorithmTaskInfo.InputFrom;

public class AlgorithmTaskGroupForm {
    
    @NotNull
    private InputFrom inputFrom;
    private String input;
    private Long task;
    private List<String> sensors;
    private String hacls;
    
    public InputFrom getInputFrom() {
        return inputFrom;
    }

    public void setInputFrom(InputFrom inputFrom) {
        this.inputFrom = inputFrom;
    }

    public Long getTask() {
        return task;
    }

    public void setTask(Long task) {
        this.task = task;
    }

    public List<String> getSensors() {
        return sensors;
    }

    public void setSensors(List<String> sensors) {
        this.sensors = sensors;
    }

    public String getHacls() {
        return hacls;
    }

    public void setHacls(String hacls) {
        this.hacls = hacls;
    }


    @NotEmpty
    private List<TaskInfoFrom> algorithms;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<TaskInfoFrom> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<TaskInfoFrom> algorithms) {
        this.algorithms = algorithms;
    }
    

    public static class TaskInfoFrom {
        @NotNull
        public String algorithm;
        @NotNull
        public InputFrom inputFrom;
        public String input;
        public Integer fromAlgorithm;
        public String getAlgorithm() {
            return algorithm;
        }
        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
        public InputFrom getInputFrom() {
            return inputFrom;
        }
        public void setInputFrom(InputFrom inputFrom) {
            this.inputFrom = inputFrom;
        }
        public String getInput() {
            return input;
        }
        public void setInput(String input) {
            this.input = input;
        }
        public Integer getFromAlgorithm() {
            return fromAlgorithm;
        }
        public void setFromAlgorithm(Integer fromAlgorithm) {
            this.fromAlgorithm = fromAlgorithm;
        }
        @Override
        public String toString() {
            return "TaskInfoFrom [algorithm=" + algorithm + ", inputFrom=" + inputFrom + ", input=" + input
                    + ", fromAlgorithm=" + fromAlgorithm + "]";
        }
    }

}
