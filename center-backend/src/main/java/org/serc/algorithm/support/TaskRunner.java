package org.serc.algorithm.support;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.serc.ApplicationContext;
import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.utils.AlgorithmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.command.WaitContainerResultCallback;

@Component
public class TaskRunner {
    
    private static final Volume CONTAINER_DATA_VOLUME = new Volume("/data");
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output";
    private static final String ERROR_NAME = "error";
    
    @Autowired
    protected DockerClient dockerClient;
    
    @Autowired
    private AlgorithmTaskRepository algorithmTaskRepository;
    
    @Async
    public void run(AlgorithmTask task) {
        try {
            task.setStatus(Status.running);
            algorithmTaskRepository.saveAndFlush(task);
            File dataDir = initData(task);
            String containerId = initContainer(task, dataDir);
            task.setContainerId(containerId);
            runContainer(task, dataDir);
            handleResult(task, dataDir);
            algorithmTaskRepository.saveAndFlush(task);
            afterRun(task, dataDir);
        } catch (Exception e) {
            task.setStatus(Status.failure);
            task.setErrorStack(AlgorithmUtils.getErrorStackString(e));
            algorithmTaskRepository.saveAndFlush(task);
            return;
        }
        
    }
    
    private File initData(AlgorithmTask task) throws IOException {
        File dataDir = new File(ApplicationContext.getDataDir(), "tasks/" + task.getId());
        dataDir.mkdir();
        FileUtils.writeStringToFile(new File(dataDir, INPUT_NAME), task.getInput(), "UTF-8");
        return dataDir;
    }
    
    private String initContainer(AlgorithmTask task, File dataDir) {
        return dockerClient.createContainerCmd(task.getAlgorithm().getImage())
                .withBinds(new Bind(ApplicationContext.toHostDir(dataDir), CONTAINER_DATA_VOLUME))
                .exec().getId();
    }
    
    private void runContainer(AlgorithmTask task, File dataDir) {
        dockerClient.startContainerCmd(task.getContainerId()).exec();
        dockerClient.waitContainerCmd(task.getContainerId()).exec(new WaitContainerResultCallback()).awaitStatusCode();
    }
    
    private void handleResult(AlgorithmTask task, File dataDir) {
        try {
            File output = new File(dataDir, OUTPUT_NAME);
            if(output.exists()) {
                task.setStatus(Status.success);
                task.setOutput(FileUtils.readFileToString(output, "UTF-8"));
                return;
            }
            task.setStatus(Status.failure);
            File errorFile = new File(dataDir, ERROR_NAME);
            if(errorFile.exists()) {
                task.setErrorStack(FileUtils.readFileToString(errorFile, "UTF-8"));
            } else {
                task.setErrorStack("unknown error, here is the log:\n" + AlgorithmUtils.getLog(task.getContainerId()));
            }
        } catch (Exception e) {
            task.setStatus(Status.failure);
            task.setErrorStack(AlgorithmUtils.getErrorStackString(e));
        }
    }
    
    private void afterRun(AlgorithmTask task, File dataDir) {
        dockerClient.removeContainerCmd(task.getContainerId()).withRemoveVolumes(true).exec();
        FileUtils.deleteQuietly(dataDir);
    }
    
}
