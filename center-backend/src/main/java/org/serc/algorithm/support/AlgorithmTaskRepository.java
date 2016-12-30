package org.serc.algorithm.support;

import org.serc.algorithm.model.Algorithm;
import org.serc.algorithm.model.AlgorithmTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmTaskRepository extends JpaRepository<AlgorithmTask, Long> {
    
    Page<AlgorithmTask> findByAlgorithm(Algorithm algorithm, Pageable pageable);

}
