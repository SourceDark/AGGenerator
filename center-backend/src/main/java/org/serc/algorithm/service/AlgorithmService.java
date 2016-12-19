package org.serc.algorithm.service;

import java.util.List;

import org.serc.algorithm.model.Algorithm;
import org.serc.algorithm.model.AlgorithmTask;

public interface AlgorithmService {
    
    Algorithm registerAlgorithm(Algorithm algorithm);
    Algorithm updateAlgorithm(Algorithm algorithm);
    List<Algorithm> getAlgorithms();
    List<AlgorithmTask> findByAlgorithm(Algorithm algorithm);
    
    AlgorithmTask run(Algorithm algorithm, String input);
    AlgorithmTask run(Algorithm algorithm, AlgorithmTask inputTask);
    AlgorithmTask wait(AlgorithmTask task);
    AlgorithmTask getAlgorithmTask(Long id);
    
}
