package org.serc.controller;

import java.util.List;

import org.serc.model.Algorithm;
import org.serc.model.AlgorithmTask;
import org.serc.service.AlgorithmTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/algorithms/{algorithm}/tasks")
public class AlgorithmTaskController {
    
    @Autowired
    private AlgorithmTaskService algorithmTaskService;
    
    @GetMapping("")
    public List<AlgorithmTask> tasks(@PathVariable Algorithm algorithm) {
        return algorithmTaskService.findByAlgorithm(algorithm);
    }
    
    @PostMapping("")
    public AlgorithmTask create(@PathVariable Algorithm algorithm,
            AlgorithmTaskForm form) throws Exception {
        System.out.println(form.getSensors());
        System.out.println(form.getHacls());
        return algorithmTaskService.create(algorithm, form.getSensors(), form.getHacls());
    }

}
