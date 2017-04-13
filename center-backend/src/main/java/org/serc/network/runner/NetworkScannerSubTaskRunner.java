package org.serc.network.runner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.serc.network.model.NetworkScannerSubTask;
import org.serc.network.model.NetworkScheduleTask.Status;
import org.serc.network.support.NetworkScannerSubTaskRepository;
import org.serc.network.support.SensorService;
import org.serc.utils.AlgorithmUtils;
import org.serc.utils.DockerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

@Component
public class NetworkScannerSubTaskRunner {
    
    @Autowired ApplicationContext applicationContext;
    @Autowired NetworkScannerSubTaskRepository  networkScannerSubTaskRepository;
    @Autowired SensorService sensorService;
    private static final String image = "registry.cn-hangzhou.aliyuncs.com/serc/agbot:openvas";
    private static final String containerDataDir = "/data";
    private static final String INPUT_NAME = "input";
    private static final Volume CONTAINER_DATA_VOLUME = new Volume("/data");
    
    @Async
    public void run(NetworkScannerSubTask task) {
        try {
            task.setStatus(Status.running);
            networkScannerSubTaskRepository.saveAndFlush(task);
            DockerClient dockerClient = DockerClientBuilder.getInstance(DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(task.getTask().getSensor().getDockerApi())).build();
            File dataDir = initData(task);
            task.setContainerId(initContainer(dockerClient, task, dataDir));
            networkScannerSubTaskRepository.saveAndFlush(task);
            runContainer(dockerClient, task);
        } catch (Exception e) {
            task.setStatus(Status.failure);
            task.setErrorStack(AlgorithmUtils.getErrorStackString(e));
            networkScannerSubTaskRepository.saveAndFlush(task);
        }catch (Error e) {
            task.setStatus(Status.failure);
            task.setErrorStack(AlgorithmUtils.getErrorStackString(e));
            networkScannerSubTaskRepository.saveAndFlush(task);
        }
    }
    
    public InspectContainerResponse.ContainerState state(DockerClient dockerClient, String id) {
        InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(id).exec();
        if (inspectContainerResponse != null) {
            return inspectContainerResponse.getState();
        } else {
            return null;
        }
    }
    
    public boolean isRunning(DockerClient dockerClient,String id) {
        InspectContainerResponse.ContainerState state = state(dockerClient, id);
        return state != null && state.getRunning();
    }
    
    @Scheduled(cron = "* */1 * * * * *")
    public void run() throws Exception {
        List<NetworkScannerSubTask> subTasks = networkScannerSubTaskRepository.findByStatus(Status.running);
        for (NetworkScannerSubTask subTask : subTasks) {
            DockerClient dockerClient = DockerClientBuilder.getInstance(DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(subTask.getTask().getSensor().getDockerApi())).build();
            if(!isRunning(dockerClient, subTask.getContainerId())) {
                try {
                    subTask.setEndTime(new Date());
                    handleResult(dockerClient, subTask);
                    subTask.setStatus(Status.success);
                    networkScannerSubTaskRepository.saveAndFlush(subTask);
                    deleteTmpResources(dockerClient, subTask, getDataDir(subTask));
                } catch (Exception e) {
                    subTask.setStatus(Status.failure);
                    subTask.setErrorStack(AlgorithmUtils.getErrorStackString(e));
                    networkScannerSubTaskRepository.saveAndFlush(subTask);
                }catch (Error e) {
                    subTask.setStatus(Status.failure);
                    subTask.setErrorStack(AlgorithmUtils.getErrorStackString(e));
                    networkScannerSubTaskRepository.saveAndFlush(subTask);
                } finally {
                    subTask.getTask().getSubTasks().stream()
                        .filter(st -> Status.created.equals(st.getStatus()))
                        .findFirst().ifPresent(st -> applicationContext.getBean(NetworkScannerSubTaskRunner.class).run(st));
                }
            }
        }
    }
    
    private File getDataDir(NetworkScannerSubTask task) {
        File dataDir = new File(org.serc.ApplicationContext.getDataDir(), "scaner-sub-tasks/" + task.getId());
        dataDir.mkdirs();
        return dataDir;
    }
    
    private File initData(NetworkScannerSubTask task) throws IOException {
        File dataDir = getDataDir(task);
        String input = "";
        for(String ip : task.getIp().split(",")) {
            input += ip + "\n";
        }
        FileUtils.writeStringToFile(new File(dataDir, INPUT_NAME), input, "UTF-8");
        return dataDir;
    }
    
    private String initContainer(DockerClient dockerClient, NetworkScannerSubTask task, File dataDir) {
        return dockerClient.createContainerCmd(image)
                .withEnv("dataDir=" + containerDataDir, 
                        "dockerHost=" + task.getTask().getSensor().getDockerApi(),
                        "ips=" + task.getIp())
                .withBinds(new Bind(org.serc.ApplicationContext.toHostDir(dataDir), CONTAINER_DATA_VOLUME))
                .exec().getId();
    }
    
    private void runContainer(DockerClient dockerClient, NetworkScannerSubTask task) {
        task.setStartTime(new Date());
        dockerClient.startContainerCmd(task.getContainerId()).exec();
    }
    
    private void handleResult(DockerClient dockerClient, NetworkScannerSubTask task) throws IOException, ArchiveException {
        File tmpDir = new File(org.serc.ApplicationContext.hostTmpDir, task.getContainerId());
        DockerUtils.copyFiles(dockerClient, task.getContainerId(), containerDataDir, tmpDir);
        String output = FileUtils.readFileToString(new File(tmpDir, "output"), "utf-8");
        sensorService.parseVulnerabilities(task.getTask().getSensor(), output);
    }
    
    private void deleteTmpResources(DockerClient dockerClient, NetworkScannerSubTask task, File dataDir) {
        if(org.serc.ApplicationContext.debug) {
            return;
        }
        dockerClient.removeContainerCmd(task.getContainerId()).withRemoveVolumes(true).exec();
        FileUtils.deleteQuietly(dataDir);
    }

}
