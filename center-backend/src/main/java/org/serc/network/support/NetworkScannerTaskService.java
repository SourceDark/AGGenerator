package org.serc.network.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.network.model.Network;
import org.serc.network.model.NetworkScannerSubTask;
import org.serc.network.model.NetworkScannerTask;
import org.serc.network.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class NetworkScannerTaskService {
    
    @Autowired NetworkScannerTaskRepository networkScannerTaskRepository;
    @Autowired NetworkScannerSubTaskRepository networkScannerSubTaskRepository;
    @Autowired NetworkScannerSubTaskRunner networkScannerSubTaskRunner;
    
    public NetworkScannerTask create(Sensor sensor, String ip){
        assert StringUtils.isNotBlank(ip);
        NetworkScannerTask networkScannerTask = new NetworkScannerTask();
        networkScannerTask.setIp(ip);
        networkScannerTask.setSensor(sensor);
        networkScannerTaskRepository.save(networkScannerTask);
        List<NetworkScannerSubTask> subTasks = Lists.newArrayList();
        for(String subip: ip.split(",")) {
            subip = subip.trim();
            NetworkScannerSubTask subTask = new NetworkScannerSubTask();
            subTask.setIp(subip);
            subTask.setStatus(Status.created);
            subTask.setTask(networkScannerTask);
            subTasks.add(subTask);
        }
        networkScannerSubTaskRepository.save(subTasks);
        networkScannerTask.setSubTasks(subTasks);
        networkScannerSubTaskRunner.run(subTasks.get(0));
        return networkScannerTask;
    }
    
    public List<NetworkScannerTask> findByNetwork(Network network) {
        return networkScannerTaskRepository.findBySensorIn(network.getSensors());
    }

}
