package org.serc.controller;

import org.serc.model.AlgorithmTask;
import org.serc.service.AlgorithmTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private AlgorithmTaskService algorithmTaskService;
    
    @GetMapping("/{task}")
    public AlgorithmTask tasks(@PathVariable AlgorithmTask task) {
        return task;
    }
}
