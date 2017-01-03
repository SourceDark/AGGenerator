package org.serc.algorithm.service;

import java.util.List;

import org.serc.algorithm.model.Algorithm;
import org.serc.algorithm.model.AlgorithmTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlgorithmService {
    
    Algorithm findOne(String idOrName);
    
    Algorithm registerAlgorithm(Algorithm algorithm);
    Algorithm updateAlgorithm(Algorithm algorithm);
    List<Algorithm> getAlgorithms();
    Page<AlgorithmTask> getTasksByAlgorithm(Algorithm algorithm, Pageable pageable);
    Page<AlgorithmTask> getTasks(Pageable pageable);
    
    AlgorithmTask run(Algorithm algorithm, String input, AlgorithmTask parentTask);
    AlgorithmTask run(Algorithm algorithm, AlgorithmTask inputTask, AlgorithmTask parentTask);
    AlgorithmTask wait(AlgorithmTask task);
    AlgorithmTask getAlgorithmTask(Long id);
    
}
