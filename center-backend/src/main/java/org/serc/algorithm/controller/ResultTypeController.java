package org.serc.algorithm.controller;

import org.serc.algorithm.model.ResultType;
import org.serc.algorithm.support.ResultTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/result-types")
public class ResultTypeController {
    
    @Autowired ResultTypeRepository resultTypeRepository;
    
    @PostMapping("")
    public ResultType create(String name) {
        ResultType resultType = new ResultType();
        resultType.setName(name);
        return resultTypeRepository.save(resultType);
    }

}
