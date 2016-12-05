package org.serc.service;

import java.io.StringWriter;
import java.util.List;

import org.serc.model.Algorithm;
import org.serc.model.AlgorithmTask;
import org.serc.model.Sensor;
import org.serc.repository.AlgorithmRepository;
import org.serc.repository.AlgorithmTaskRepository;
import org.serc.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

@Service
public class AlgorithmTaskService {
    
    @Autowired
    private SensorRepository sensorRepository;
    
    @Autowired
    private AlgorithmTaskRepository taskRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private AlgorithmRepository algorithmRepository;
    
    @Autowired
    private TaskRunner taskRunner;
    
    public List<AlgorithmTask> findByAlgorithm(Algorithm algorithm) {
        return taskRepository.findByAlgorithm(algorithm);
    }
    
    public AlgorithmTask create(Algorithm algorithm, List<String> sensorIds, String hacls) throws Exception {
        AlgorithmTask task = new AlgorithmTask();
        task.setAlgorithm(algorithm);
        task.setStatus(org.serc.model.AlgorithmTask.Status.running);
        StringWriter inputWriter= new StringWriter();
        List<Sensor> sensors = sensorRepository.findByNameIn(sensorIds);
        objectMapper.writeValue(inputWriter, ImmutableMap.of("sensors", sensors, "hacls", hacls));
        task.setInput(inputWriter.toString());
        task = taskRepository.save(task);
        taskRunner.run(task);
        return task;
    }

}
