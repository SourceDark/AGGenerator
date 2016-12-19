package org.serc.algorithm.support;

import org.serc.algorithm.model.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

}
