package org.serc.algorithm.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.serc.algorithm.controller.dto.AlgorithmDto;
import org.serc.algorithm.controller.form.AlgorithmForm;
import org.serc.algorithm.model.Algorithm;
import org.serc.algorithm.model.ResultType;
import org.serc.algorithm.service.AlgorithmService;
import org.serc.algorithm.support.ResultTypeRepository;
import org.serc.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/algorithms")
public class AlgorithmController {
    
    @Autowired AlgorithmService algorithmService;
    @Autowired ResultTypeRepository resultTypeRepository;
    
    @GetMapping("")
    public List<AlgorithmDto> algorithms() {
        return algorithmService.getAlgorithms().stream().map(AlgorithmDto::new).collect(Collectors.toList());
    }
    
    @GetMapping("/{algorithm}")
    public AlgorithmDto algorithm(Algorithm algorithm) {
        return new AlgorithmDto(algorithm);
    }
    
    @PostMapping("")
    public AlgorithmDto algorithm(@Valid AlgorithmForm form) {
        ResultType inputType = resultTypeRepository.findOne(form.getInputType());
        ResultType outputType = resultTypeRepository.findOne(form.getInputType());
        if(inputType == null || outputType == null) {
            throw new ResourceNotFoundException("result type not found");
        }
        Algorithm algorithm = new Algorithm();
        algorithm.setImage(form.getImage());
        algorithm.setName(form.getName());
        algorithm.setInputType(inputType);
        algorithm.setOutputType(outputType);
        return new AlgorithmDto(algorithmService.registerAlgorithm(algorithm));
    }

}
