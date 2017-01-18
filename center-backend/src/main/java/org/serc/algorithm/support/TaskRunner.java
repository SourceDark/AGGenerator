package org.serc.algorithm.support;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.serc.ApplicationContext;
import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.utils.AlgorithmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.google.common.collect.Maps;

@Component
public class TaskRunner {
    
    private static final Volume CONTAINER_DATA_VOLUME = new Volume("/data");
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output";
    private static final String ERROR_NAME = "error";
    private static final String API_NAME = "api";
    
    @Autowired DockerClient dockerClient;
    @Autowired AlgorithmTaskRepository algorithmTaskRepository;
    @Autowired ApplicationContext applicationContext;
    @Autowired org.springframework.context.ApplicationContext springContext;
    private TaskRunner self;
    
    @PostConstruct
    private void initProxySelf(){
        self = springContext.getBean(TaskRunner.class);
    }
    
    @Async
    public void run(AlgorithmTask task) {
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
    
    private File initData(AlgorithmTask task) throws IOException {
        File dataDir = new File(ApplicationContext.getDataDir(), "tasks/" + task.getId());
        dataDir.mkdir();
        FileUtils.writeStringToFile(new File(dataDir, INPUT_NAME), task.getInput(), "UTF-8");
        
        Map<String, Map<String, String>> api = Maps.newHashMap();
        Map<String, String> hostApi = Maps.newHashMap();
        hostApi.put("center_api", ApplicationContext.centerApi);
        hostApi.put("cve_api", ApplicationContext.cveApi);
        api.put("host", hostApi);
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter inputWriter= new StringWriter();
        objectMapper.writeValue(inputWriter, api);
        FileUtils.write(new File(dataDir, API_NAME), inputWriter.toString(), "UTF-8");
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
    
    private void deleteTmpResources(AlgorithmTask task, File dataDir) {
        if(ApplicationContext.debug) {
            return;
        }
        dockerClient.removeContainerCmd(task.getContainerId()).withRemoveVolumes(true).exec();
        FileUtils.deleteQuietly(dataDir);
    }
    
    private void runNextTask(AlgorithmTask source) {
        for(AlgorithmTask next: algorithmTaskRepository.findByInputTaskAndStatus(source, Status.created)){
            if(Status.success.equals(source.getStatus())) {
                next.setInput(source.getOutput());
                self.run(next);
            } else {
                next.setStatus(Status.failure);
                next.setErrorStack("input task is failure!!!");
                algorithmTaskRepository.save(next);
                runNextTask(next);
            }
            
        }
    }
    
}
