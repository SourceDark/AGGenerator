package org.serc.algorithm.support;

import java.util.List;

import org.serc.algorithm.model.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
    
    Algorithm findByName(String name);
    List<Algorithm> findByDeletedFalse();
    
}
