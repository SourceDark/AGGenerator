package org.serc.network.support;

import java.util.List;

import org.serc.network.model.NetworkScannerSubTask;
import org.serc.network.model.NetworkScheduleTask.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkScannerSubTaskRepository extends JpaRepository<NetworkScannerSubTask, Long> {
    
    List<NetworkScannerSubTask> findByStatus(Status status);

}
