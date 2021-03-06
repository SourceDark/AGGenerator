package org.serc.network.support;

import java.util.List;

import org.serc.network.model.Network;
import org.serc.network.model.NetworkScheduleTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkScheduleTaskRepository extends JpaRepository<NetworkScheduleTask, Long> {
    
    NetworkScheduleTask findTopByNetworkOrderByIdDesc(Network network);
    List<NetworkScheduleTask> findByNetworkOrderByIdDesc(Network network);

}
