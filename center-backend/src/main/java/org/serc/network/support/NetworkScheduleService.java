package org.serc.network.support;

import java.io.StringWriter;
import java.util.List;

import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.model.AlgorithmTaskInfo;
import org.serc.algorithm.model.AlgorithmTaskInfo.InputFrom;
import org.serc.algorithm.service.AlgorithmService;
import org.serc.network.model.Network;
import org.serc.network.model.NetworkScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

@Component
public class NetworkScheduleService {
    
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
            
            AlgorithmTaskInfo mulval = new AlgorithmTaskInfo();
            mulval.setAlgorithm(algorithmService.findOne("6"));
            mulval.setInputFrom(InputFrom.source);
            mulval.setInput(input);
            AlgorithmTaskInfo attackPathCount = new AlgorithmTaskInfo();
            attackPathCount.setAlgorithm(algorithmService.findOne("11"));
            attackPathCount.setInputFrom(InputFrom.algorithm);
            attackPathCount.setFromAlgorithm(0);
            AlgorithmTaskInfo analysisMetric = new AlgorithmTaskInfo();
            analysisMetric.setAlgorithm(algorithmService.findOne("9"));
            analysisMetric.setInputFrom(InputFrom.algorithm);
            analysisMetric.setFromAlgorithm(0);
            AlgorithmTaskInfo weakestAdversaryMetric = new AlgorithmTaskInfo();
            weakestAdversaryMetric.setAlgorithm(algorithmService.findOne("10"));
            weakestAdversaryMetric.setInputFrom(InputFrom.algorithm);
            weakestAdversaryMetric.setFromAlgorithm(0);
            List<AlgorithmTask> tasks = algorithmService.runTaskGroup(Lists.newArrayList(
                    mulval, attackPathCount, analysisMetric, weakestAdversaryMetric), input);
            
            NetworkScheduleTask networkScheduleTask = new NetworkScheduleTask();
            networkScheduleTask.setAlgorithmTasks(Lists.newArrayList(tasks.subList(1, tasks.size())));
            networkScheduleTask.setNetwork(network);
            networkScheduleTaskRepository.save(networkScheduleTask);
        }
    }

}
