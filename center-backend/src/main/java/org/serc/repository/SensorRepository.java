package org.serc.repository;

import java.util.List;

import org.serc.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    
    List<Sensor> findByNameIn(List<String> ids);

}
