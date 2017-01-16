package org.serc.network.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.serc.network.controller.dto.NetworkListDto;
import org.serc.network.model.Network;
import org.serc.network.model.NetworkScheduleTask;
import org.serc.network.support.NetworkRepository;
import org.serc.network.support.NetworkScheduleService;
import org.serc.network.support.NetworkScheduleTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/networks")
public class NetworkController {
    
    @Autowired NetworkRepository networkRepository;
    @Autowired NetworkScheduleService networkScheduleService;
    @Autowired NetworkScheduleTaskRepository networkScheduleTaskRepository;
    
    @GetMapping("")
    public List<NetworkListDto> networks() {
        return networkRepository.findAll().stream()
                .map(network -> new NetworkListDto(network, networkScheduleTaskRepository.findTopByNetworkOrderByIdDesc(network)))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/schedule")
    public void schedule() throws Exception {
        networkScheduleService.run();
    }
    
    @GetMapping("/{network}")
    public NetworkListDto network(Network network) {
        return new NetworkListDto(network);
    }
    
    @GetMapping("/{network}/scores")
    public Map<Long, Map<String, Object>> scores(Network network) {
        return networkScheduleTaskRepository.findByNetworkOrderByIdDesc(network).stream()
                .collect(Collectors.toMap(n -> n.getCreatedTime().getTime(), NetworkScheduleTask::scores));
    }

}
