package org.serc.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.serc.ApplicationContext;
import org.serc.model.AlgorithmResult;
import org.serc.model.AlgorithmTask;
import org.serc.model.AlgorithmTask.Status;
import org.serc.repository.AlgorithmResultRepository;
import org.serc.repository.AlgorithmTaskRepository;
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
    
    @Autowired
    protected DockerClient dockerClient;
    
    @Autowired
    private AlgorithmResultRepository algorithmResultRepository;
    
    @Autowired
    private AlgorithmTaskRepository algorithmTaskRepository;
    
    @Async
    public void run(AlgorithmTask task) throws IOException, InterruptedException {
        task.setStatus(Status.running);
        algorithmTaskRepository.saveAndFlush(task);
        File dataDir = initData(task);
        String containerId = initContainer(task, dataDir);
        task.setContainerId(containerId);
        String output = runContainer(task, dataDir);
        AlgorithmResult result = initResult(task, output);
        task.setResult(result);
        task.setStatus(Status.success);
        algorithmTaskRepository.saveAndFlush(task);
        afterRun(task, dataDir);
    }
    
    private File initData(AlgorithmTask task) throws IOException {
        File dataDir = new File(ApplicationContext.getDataDir(), "tasks/" + task.getId());
        dataDir.mkdir();
        FileUtils.writeStringToFile(new File(dataDir, INPUT_NAME), task.getInput(), "UTF-8");
        return dataDir;
    }
    
    private String initContainer(AlgorithmTask task, File dataDir) {
        return dockerClient.createContainerCmd(task.getAlgorithm().getControlImage())
                .withBinds(new Bind(ApplicationContext.toHostDir(dataDir), CONTAINER_DATA_VOLUME))
                .exec().getId();
    }
    
    private String runContainer(AlgorithmTask task, File dataDir) throws IOException {
        dockerClient.startContainerCmd(task.getContainerId()).exec();
        dockerClient.waitContainerCmd(task.getContainerId()).exec(new WaitContainerResultCallback()).awaitStatusCode();
        return FileUtils.readFileToString(new File(dataDir, OUTPUT_NAME), "UTF-8");
    }
    
    private AlgorithmResult initResult(AlgorithmTask task, String output) {
        AlgorithmResult result = new AlgorithmResult();
        result.setAlgorithm(task.getAlgorithm());
        result.setContent(output);
        return algorithmResultRepository.save(result);
    }
    
    private void afterRun(AlgorithmTask task, File dataDir) throws IOException {
        dockerClient.removeContainerCmd(task.getContainerId()).withRemoveVolumes(true).exec();
        FileUtils.deleteQuietly(dataDir);
    }
    
}
