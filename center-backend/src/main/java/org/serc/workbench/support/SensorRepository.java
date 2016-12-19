package org.serc.workbench.support;

import java.util.List;

import org.serc.workbench.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    
    List<Sensor> findByNameIn(List<String> ids);

}
