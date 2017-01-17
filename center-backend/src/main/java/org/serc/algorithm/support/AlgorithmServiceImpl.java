package org.serc.algorithm.support;

import java.util.List;

import org.serc.algorithm.model.Algorithm;
import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.model.AlgorithmTask.Status;
import org.serc.algorithm.model.AlgorithmTaskInfo;
import org.serc.algorithm.model.ResultType;
import org.serc.algorithm.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.google.common.collect.Lists;

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
        return algorithmRepository.findByDeletedFalse();
    }

    @Override
    public AlgorithmTask run(Algorithm algorithm, String input, AlgorithmTask parentTask) {
        AlgorithmTask task = new AlgorithmTask();
        task.setAlgorithm(algorithm);
        task.setStatus(org.serc.algorithm.model.AlgorithmTask.Status.created);
        task.setInput(input);
        task.setInputType(algorithm.getInputType());
        task.setOutputType(algorithm.getOutputType());
        if(task.getParentTask() != null) {
            if(parentTask.getParentTask() != null) {
                task.setParentTask(parentTask.getParentTask());
            } else {
                task.setParentTask(parentTask);
            }
        }
        task = taskRepository.save(task);
        taskRunner.run(task);
        return task;
    }
    
    @Override
    public AlgorithmTask run(Algorithm algorithm, AlgorithmTask inputTask, AlgorithmTask parentTask) {
        AlgorithmTask task = run(algorithm, inputTask.getOutput(), parentTask);
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

    @Override
    public Algorithm findOne(String idOrName) {
        Algorithm algorithm = algorithmRepository.findByName(idOrName);
        if(algorithm == null) {
            try {
                Long id = Long.parseLong(idOrName);
                algorithm = algorithmRepository.findOne(id);
            } catch (Exception e) {
                return null;
            }
        }
        return algorithm;
    }

    @Override
    public List<AlgorithmTask> runTaskGroup(List<AlgorithmTaskInfo> taskInfos, String input) {
        // init tasks
        List<AlgorithmTask> algorithmTasks = Lists.newArrayList();
        for(AlgorithmTaskInfo taskInfo: taskInfos) {
            AlgorithmTask task = new AlgorithmTask();
            task.setAlgorithm(taskInfo.getAlgorithm());
            task.setInputType(taskInfo.getAlgorithm().getInputType());
            task.setOutputType(taskInfo.getAlgorithm().getOutputType());
            task.setStatus(org.serc.algorithm.model.AlgorithmTask.Status.created);
            if(AlgorithmTaskInfo.InputFrom.direct.equals(taskInfo.getInputFrom())) {
                task.setInput(taskInfo.getInput());
            } else if (AlgorithmTaskInfo.InputFrom.source.equals(taskInfo.getInputFrom())) {
                task.setInput(input);
            }
            algorithmTasks.add(task);
        }
        // handle task dependency
        algorithmTasks = taskRepository.save(algorithmTasks);
        for(int i = 0; i < taskInfos.size(); i++) {
            AlgorithmTask targetTask = algorithmTasks.get(i);
            AlgorithmTaskInfo taskInfo = taskInfos.get(i);
            targetTask.setParentTask(algorithmTasks.get(0));
            if(AlgorithmTaskInfo.InputFrom.algorithm.equals(taskInfo.getInputFrom())) {
                AlgorithmTask sourceTask = algorithmTasks.get(taskInfo.getFromAlgorithm());
                targetTask.setInputTask(sourceTask);
            }
        }
        algorithmTasks = taskRepository.save(algorithmTasks);
        // run tasks
        for(AlgorithmTask task: algorithmTasks) {
            if(task.getInput() != null) {
                taskRunner.run(task);
            }
        }
        return algorithmTasks;
    }

    @Override
    public List<Algorithm> getAlgorithmsByInputType(ResultType inputType) {
        return algorithmRepository.findByInputType(inputType);
    }

    @Override
    public List<Algorithm> getAlgorithmsByOutputType(ResultType outputType) {
        return algorithmRepository.findByOutputType(outputType);
    }

}
