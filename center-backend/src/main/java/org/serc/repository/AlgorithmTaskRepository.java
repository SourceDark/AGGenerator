package org.serc.repository;

import java.util.List;

import org.serc.model.Algorithm;
import org.serc.model.AlgorithmTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmTaskRepository extends JpaRepository<AlgorithmTask, Long> {
    
    List<AlgorithmTask> findByAlgorithm(Algorithm algorithm);

}
