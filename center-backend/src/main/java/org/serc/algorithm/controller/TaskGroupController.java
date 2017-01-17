package org.serc.algorithm.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import jersey.repackaged.com.google.common.collect.Lists;

import org.serc.algorithm.controller.dto.AlgorithmTaskListDto;
import org.serc.algorithm.controller.form.AlgorithmTaskGroupForm;
import org.serc.algorithm.controller.form.AlgorithmTaskGroupForm.TaskInfoFrom;
import org.serc.algorithm.model.Algorithm;
import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.model.AlgorithmTaskInfo;
import org.serc.algorithm.model.AlgorithmTaskInfo.InputFrom;
import org.serc.algorithm.service.AlgorithmService;
import org.serc.exception.BadRequestException;
import org.serc.exception.ResourceNotFoundException;
import org.serc.network.model.Sensor;
import org.serc.network.support.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping("/taskgroups")
public class TaskGroupController {
    
    @Autowired AlgorithmService algorithmService;
    @Autowired ObjectMapper objectMapper;
    @Autowired SensorRepository sensorRepository;
    
    private List<AlgorithmTaskInfo> toAlgorithmTaskInfos(AlgorithmTaskGroupForm form) {
        List<AlgorithmTaskInfo> algorithmTaskInfos = Lists.newArrayList();
        for(TaskInfoFrom taskInfoForm: form.getAlgorithms()) {
            AlgorithmTaskInfo algorithmTaskInfo = new AlgorithmTaskInfo();
            Algorithm algorithm = algorithmService.findOne(taskInfoForm.algorithm);
            if(algorithm == null) {
                throw new ResourceNotFoundException(taskInfoForm.algorithm + " not found");
            }
            algorithmTaskInfo.setAlgorithm(algorithm);
            algorithmTaskInfo.setInputFrom(taskInfoForm.inputFrom);
            if(InputFrom.algorithm.equals(algorithmTaskInfo.getInputFrom())) {
                if(taskInfoForm.inputAlgorithm >= form.getAlgorithms().size()) {
                    throw new BadRequestException( "fromAlgorithm index" + taskInfoForm.inputAlgorithm + " is larger than algorithms list!");
                }
                algorithmTaskInfo.setFromAlgorithm(taskInfoForm.inputAlgorithm);
            }
            algorithmTaskInfo.setInput(taskInfoForm.input);
            algorithmTaskInfos.add(algorithmTaskInfo);
        }
        return algorithmTaskInfos;
    }
    
    @PostMapping("")
    public List<AlgorithmTaskListDto> run(@Valid AlgorithmTaskGroupForm form) throws JsonGenerationException, JsonMappingException, IOException {
        String input = form.getInput();
        if(InputFrom.network.equals(form.getInputFrom())) {
            StringWriter inputWriter= new StringWriter();
            List<Sensor> sensors = sensorRepository.findByNameIn(form.getSensors());
            objectMapper.writeValue(inputWriter, ImmutableMap.of("sensors", sensors, "hacls", form.getHacls()));
            input = inputWriter.toString();
        } else if (InputFrom.task.equals(form.getInputFrom())) {
            AlgorithmTask sourceTask = algorithmService.getAlgorithmTask(form.getTask());
            if(sourceTask == null) {
                throw new ResourceNotFoundException("task " + form.getTask() + " not found");
            }
            input = sourceTask.getOutput();
        }
        return algorithmService.runTaskGroup(toAlgorithmTaskInfos(form), input).stream()
                .map(AlgorithmTaskListDto::new).collect(Collectors.toList());
    }

}
