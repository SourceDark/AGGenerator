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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;

@RestController
@RequestMapping("/algorithms")
public class AlgorithmController {
    
    @Autowired AlgorithmService algorithmService;
    @Autowired ResultTypeRepository resultTypeRepository;
    @Autowired DockerClient dockerClient;
    
    private AlgorithmDto setImageExist(List<Image> images, AlgorithmDto algorithmDto) {
        for(Image image : images) {
            if(image.getId().equals(algorithmDto.getImage())) {
                algorithmDto.setImageExist(true);
                return algorithmDto;
            }
            if(image.getRepoTags() != null) {
                for(String tag: image.getRepoTags()) {
                    if(tag.equals(algorithmDto.getImage())) {
                        algorithmDto.setImageExist(true);
                        return algorithmDto;
                    }
                }
            }
        }
        return algorithmDto;
    }
    
    @GetMapping("")
    public List<AlgorithmDto> algorithms() {
        List<Image> images = dockerClient.listImagesCmd().exec();
        return algorithmService.getAlgorithms().stream().map(AlgorithmDto::new).map(a -> {
            return setImageExist(images, a);
        }).collect(Collectors.toList());
    }
    
    @GetMapping("/{algorithmIdOrName}")
    public AlgorithmDto algorithm(@PathVariable String algorithmIdOrName) {
        Algorithm algorithm = algorithmService.findOne(algorithmIdOrName);
        if(algorithm == null) {
            throw new ResourceNotFoundException();
        }
        return setImageExist(dockerClient.listImagesCmd().exec(), new AlgorithmDto(algorithm));
    }
    
    @PostMapping("")
    public AlgorithmDto algorithm(@Valid AlgorithmForm form) {
        ResultType inputType = resultTypeRepository.findOne(form.getInputType());
        ResultType outputType = resultTypeRepository.findOne(form.getOutputType());
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
    
    @PutMapping("/{algorithm}")
    public AlgorithmDto update(@PathVariable Algorithm algorithm, AlgorithmForm form) {
        if(form.getInputType() != null) {
            ResultType inputType = resultTypeRepository.findOne(form.getInputType());
            if(inputType == null) {
                throw new ResourceNotFoundException("result type not found");
            }
            algorithm.setInputType(inputType);
        }
        if(form.getOutputType() != null) {
            ResultType outputType = resultTypeRepository.findOne(form.getOutputType());
            if(outputType == null) {
                throw new ResourceNotFoundException("result type not found");
            }
            algorithm.setOutputType(outputType);
        }
        if(form.getName() != null) {
            algorithm.setName(form.getName());
        }
        if(form.getImage() != null) {
            algorithm.setImage(form.getImage());
        }
        return new AlgorithmDto(algorithmService.updateAlgorithm(algorithm));
    }

}
