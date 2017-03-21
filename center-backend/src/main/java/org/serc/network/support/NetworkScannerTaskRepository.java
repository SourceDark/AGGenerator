package org.serc.network.support;

import java.util.List;

import org.serc.network.model.NetworkScannerTask;
import org.serc.network.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkScannerTaskRepository extends JpaRepository<NetworkScannerTask, Long> {
    
    List<NetworkScannerTask> findBySensorIn(List<Sensor> sensors);

}
