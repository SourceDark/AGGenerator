package org.serc.algorithm.controller;

import org.serc.algorithm.controller.dto.AlgorithmTaskDto;
import org.serc.algorithm.model.AlgorithmTask;
import org.serc.algorithm.support.AlgorithmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private AlgorithmServiceImpl algorithmTaskService;
    
    @GetMapping("/{task}")
    public AlgorithmTaskDto getOne(@PathVariable AlgorithmTask task) {
        return new AlgorithmTaskDto(task);
    }
    
    @GetMapping("/{task}/wait")
    public AlgorithmTaskDto wait(@PathVariable AlgorithmTask task) {
        return new AlgorithmTaskDto(algorithmTaskService.wait(task));
    }
}