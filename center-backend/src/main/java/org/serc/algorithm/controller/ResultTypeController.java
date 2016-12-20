package org.serc.algorithm.controller;

import java.util.List;

import org.serc.algorithm.model.ResultType;
import org.serc.algorithm.support.ResultTypeRepository;
import org.serc.exception.ActionForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/result-types")
public class ResultTypeController {
    
    @Autowired ResultTypeRepository resultTypeRepository;
    
    @PostMapping("")
    public ResultType create(ResultType resultType) {
        if(resultTypeRepository.findOne(resultType.getName()) != null) {
            throw new ActionForbiddenException("name already exist");
        }
        return resultTypeRepository.save(resultType);
    }
    
    @GetMapping("")
    public List<ResultType> all() {
        return resultTypeRepository.findAll();
    }

}
