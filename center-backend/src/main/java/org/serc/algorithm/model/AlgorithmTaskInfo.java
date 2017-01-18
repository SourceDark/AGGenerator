package org.serc.algorithm.model;

public class AlgorithmTaskInfo {
    
    public enum InputFrom {
        direct, source, algorithm, network, task;
    }
    
    private Algorithm algorithm;
    private InputFrom inputFrom;
    private String input;
    private Integer fromAlgorithm;
    
    public AlgorithmTaskInfo() { }
    
    public AlgorithmTaskInfo(Algorithm algorithm, InputFrom inputFrom, String input) {
        this.algorithm = algorithm;
        this.inputFrom = inputFrom;
        this.input = input;
    }
    
    public AlgorithmTaskInfo(Algorithm algorithm, InputFrom inputFrom, Integer fromAlgorithm) {
        this.algorithm = algorithm;
        this.inputFrom = inputFrom;
        this.fromAlgorithm = fromAlgorithm;
    }
    
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
    public Integer getFromAlgorithm() {
        return fromAlgorithm;
    }
    public void setFromAlgorithm(Integer fromAlgorithm) {
        this.fromAlgorithm = fromAlgorithm;
    }
    public InputFrom getInputFrom() {
        return inputFrom;
    }
    public void setInputFrom(InputFrom inputFrom) {
        this.inputFrom = inputFrom;
    }

}
