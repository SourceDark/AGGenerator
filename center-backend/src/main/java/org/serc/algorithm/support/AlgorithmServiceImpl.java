package org.serc.algorithm.support;

import java.util.List;

import org.serc.algorithm.model.Algorithm;
import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.algorithm.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.command.WaitContainerResultCallback;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {
    
    @Autowired AlgorithmTaskRepository taskRepository;
    @Autowired AlgorithmRepository algorithmRepository;
    @Autowired TaskRunner taskRunner;
    @Autowired DockerClient dockerClient;
    
    @Override
    public Page<AlgorithmTask> getTasksByAlgorithm(Algorithm algorithm, Pageable pageable) {
        return taskRepository.findByAlgorithm(algorithm, pageable);
    }
    
    @Override
    public Page<AlgorithmTask> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }
    
    @Override
    public Algorithm registerAlgorithm(Algorithm algorithm) {
        return algorithmRepository.save(algorithm);
    }

    @Override
    public Algorithm updateAlgorithm(Algorithm algorithm) {
        return algorithmRepository.save(algorithm);
    }

    @Override
    public List<Algorithm> getAlgorithms() {
        return algorithmRepository.findAll();
    }

    @Override
    public AlgorithmTask run(Algorithm algorithm, String input) {
        AlgorithmTask task = new AlgorithmTask();
        task.setAlgorithm(algorithm);
        task.setStatus(org.serc.algorithm.model.AlgorithmTask.Status.created);
        task.setInput(input);
        task = taskRepository.save(task);
        taskRunner.run(task);
        return task;
    }
    
    @Override
    public AlgorithmTask run(Algorithm algorithm, AlgorithmTask inputTask) {
        AlgorithmTask task = run(algorithm, inputTask.getOutput());
        task.setInputTask(inputTask);
        return taskRepository.save(task);
    }

    @Override
    public AlgorithmTask wait(AlgorithmTask task) {
        if(!Status.running.equals(task.getStatus())) {
            return task;
        }
        dockerClient.waitContainerCmd(task.getContainerId()).exec(new WaitContainerResultCallback()).awaitStatusCode();
        taskRepository.flush();
        return getAlgorithmTask(task.getId());
    }

    @Override
    public AlgorithmTask getAlgorithmTask(Long id) {
        return taskRepository.findOne(id);
    }

}
