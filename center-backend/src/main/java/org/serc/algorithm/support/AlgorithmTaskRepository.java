package org.serc.algorithm.support;

import java.util.List;

import org.serc.algorithm.model.Algorithm;
import org.serc.algorithm.model.AlgorithmTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmTaskRepository extends JpaRepository<AlgorithmTask, Long> {
    
    List<AlgorithmTask> findByAlgorithm(Algorithm algorithm);

}
