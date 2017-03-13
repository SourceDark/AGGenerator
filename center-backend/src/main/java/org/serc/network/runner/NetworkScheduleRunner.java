package org.serc.network.runner;

import java.io.StringWriter;
import java.util.List;

import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.model.AlgorithmTaskInfo;
import org.serc.algorithm.model.AlgorithmTaskInfo.InputFrom;
import org.serc.algorithm.service.AlgorithmService;
import org.serc.network.model.Network;
import org.serc.network.model.NetworkScheduleTask;
import org.serc.network.support.NetworkRepository;
import org.serc.network.support.NetworkScheduleTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

@Component
public class NetworkScheduleRunner {
    
    @Autowired NetworkRepository networkRepository;
    @Autowired ObjectMapper objectMapper;
    @Autowired AlgorithmService algorithmService;
    @Autowired NetworkScheduleTaskRepository networkScheduleTaskRepository;
    
    @Scheduled(cron = "0 2 0 * * ?")
    public void run() throws Exception {
        for(Network network: networkRepository.findAll()) {
            StringWriter inputWriter= new StringWriter();
            objectMapper.writeValue(inputWriter, ImmutableMap.of("sensors", network.getSensors(), "hacls", network.getHacls()));
            String input = inputWriter.toString();
            
            List<AlgorithmTask> tasks = algorithmService.runTaskGroup(Lists.newArrayList(
                    new AlgorithmTaskInfo(algorithmService.findOne("6"), InputFrom.source, input),
                    new AlgorithmTaskInfo(algorithmService.findOne("12"), InputFrom.source, input),
                    new AlgorithmTaskInfo(algorithmService.findOne("1"), InputFrom.source, input),
                    new AlgorithmTaskInfo(algorithmService.findOne("9"), InputFrom.algorithm, 0), 
                    new AlgorithmTaskInfo(algorithmService.findOne("10"), InputFrom.algorithm, 0),
                    new AlgorithmTaskInfo(algorithmService.findOne("13"), InputFrom.algorithm, 0),
                    new AlgorithmTaskInfo(algorithmService.findOne("11"), InputFrom.algorithm, 0),
                    new AlgorithmTaskInfo(algorithmService.findOne("10"), InputFrom.algorithm, 1),
                    new AlgorithmTaskInfo(algorithmService.findOne("13"), InputFrom.algorithm, 1),
                    new AlgorithmTaskInfo(algorithmService.findOne("11"), InputFrom.algorithm, 1),
                    new AlgorithmTaskInfo(algorithmService.findOne("14"), InputFrom.algorithm, 2)
                    ), input);
            NetworkScheduleTask networkScheduleTask = new NetworkScheduleTask();
            networkScheduleTask.setAlgorithmTasks(Lists.newArrayList(tasks.subList(1, tasks.size())));
            networkScheduleTask.setNetwork(network);
            networkScheduleTaskRepository.save(networkScheduleTask);
        }
    }

}
