package org.serc.network.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.serc.network.controller.dto.NetworkListDto;
import org.serc.network.support.NetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/networks")
public class NetworkController {
    
    @Autowired NetworkRepository networkRepository;
    
    @RequestMapping("")
    public List<NetworkListDto> networks() {
        return networkRepository.findAll().stream().map(NetworkListDto::new).collect(Collectors.toList());
    }

}
