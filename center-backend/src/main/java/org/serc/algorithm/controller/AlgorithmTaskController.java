package org.serc.algorithm.controller;

import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.serc.algorithm.controller.dto.AlgorithmTaskDto;
import org.serc.algorithm.controller.dto.AlgorithmTaskListDto;
import org.serc.algorithm.controller.form.AlgorithmGenerationTaskForm;
import org.serc.algorithm.model.Algorithm;
import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.service.AlgorithmService;
import org.serc.exception.ActionForbiddenException;
import org.serc.exception.ResourceNotFoundException;
import org.serc.network.model.Sensor;
import org.serc.network.support.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping("/algorithms/{algorithmIdOrName}/tasks")
public class AlgorithmTaskController {
    
    @Autowired
    private AlgorithmService algorithmService;
    
    @Autowired
    private SensorRepository sensorRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @ModelAttribute("algorithm")
    public Algorithm algorithm(@PathVariable String algorithmIdOrName) {
        Algorithm algorithm = algorithmService.findOne(algorithmIdOrName);
        if(algorithm == null) {
            throw new ResourceNotFoundException();
        }
        return algorithm;
    }
    
    @ModelAttribute("parentTask")
    public AlgorithmTask algorithm(@RequestParam(required = false) Long parentTask) {
        if(parentTask != null) {
            return algorithmService.getAlgorithmTask(parentTask);
        }
        return null;
    }
    
    @GetMapping("")
    public List<AlgorithmTaskListDto> tasks(Algorithm algorithm,
            @PageableDefault(sort = "createdTime", direction = Direction.DESC) Pageable pageable) {
        return algorithmService.getTasksByAlgorithm(algorithm, pageable).getContent().stream()
                .map(AlgorithmTaskListDto::new).collect(Collectors.toList());
    }

    @PostMapping("")
    public AlgorithmTaskDto run(Algorithm algorithm, String input, AlgorithmTask parentTask) throws Exception {
        return new AlgorithmTaskDto(algorithmService.run(algorithm, input, parentTask));
    }
    
    @PostMapping("/generation")
    public AlgorithmTaskDto run(Algorithm algorithm, AlgorithmGenerationTaskForm form, AlgorithmTask parentTask) throws Exception {
        if(!algorithm.getInputType().getName().equals("network")) {
            throw new ActionForbiddenException(String.format("%s is not a generation algorithm", algorithm.getName()));
        }
        StringWriter inputWriter= new StringWriter();
        List<Sensor> sensors = sensorRepository.findByNameIn(form.getSensors());
        objectMapper.writeValue(inputWriter, ImmutableMap.of("sensors", sensors, "hacls", form.getHacls()));
        return new AlgorithmTaskDto(algorithmService.run(algorithm, inputWriter.toString(), parentTask));
    }
    
    @PostMapping("/analysis")
    public AlgorithmTaskDto analysis(@PathVariable Algorithm algorithm, @RequestParam("task") AlgorithmTask task, AlgorithmTask parentTask) throws Exception {
        return new AlgorithmTaskDto(algorithmService.run(algorithm, task, parentTask));
    }
    
}
