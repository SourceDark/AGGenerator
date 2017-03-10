package org.serc.network.support;

import java.io.File;

import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.network.model.NetworkScannerTask;
import org.serc.utils.AlgorithmUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NetworkCanner {
    
    @Async
    public void run(NetworkScannerTask task) {
        try {
            task.setStatus(Status.running);
            algorithmTaskRepository.saveAndFlush(task);
            File dataDir = initData(task);
            String containerId = initContainer(task, dataDir);
            task.setContainerId(containerId);
            algorithmTaskRepository.saveAndFlush(task);
            runContainer(task, dataDir);
            handleResult(task, dataDir);
            algorithmTaskRepository.saveAndFlush(task);
            deleteTmpResources(task, dataDir);
        } catch (Exception e) {
            task.setStatus(Status.failure);
            task.setErrorStack(AlgorithmUtils.getErrorStackString(e));
            algorithmTaskRepository.saveAndFlush(task);
        } finally {
            runNextTask(task);
        }
        
    }

}
