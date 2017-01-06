package org.serc.network.support;

import java.util.List;

import org.serc.network.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    
    List<Sensor> findByNameIn(List<String> ids);
    Sensor findByName(String name);

}
