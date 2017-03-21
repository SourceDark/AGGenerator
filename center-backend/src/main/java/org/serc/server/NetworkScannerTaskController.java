package org.serc.server;

import java.util.List;
import java.util.stream.Collectors;

import org.serc.exception.ResourceNotFoundException;
import org.serc.network.controller.dto.NetworkScannerTaskDto;
import org.serc.network.controller.form.NetworkScannerTaskForm;
import org.serc.network.model.Network;
import org.serc.network.model.Sensor;
import org.serc.network.support.NetworkScannerTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server/{network}/tasks")
public class NetworkScannerTaskController {
    
    @Autowired NetworkScannerTaskService networkScannerTaskService;
     
    @PostMapping("")
    public NetworkScannerTaskDto task(@PathVariable Network network, NetworkScannerTaskForm form) {
        Sensor sensor = network.getSensors().stream().filter(n -> n.getName().equals(form.getSensor()))
                .findFirst().orElseThrow(ResourceNotFoundException::new);
        return new NetworkScannerTaskDto(networkScannerTaskService.create(sensor, form.getIp()));
    }
    
    @GetMapping("")
    public List<NetworkScannerTaskDto> task(Network network) {
        return networkScannerTaskService.findByNetwork(network).stream().map(NetworkScannerTaskDto::new)
                .collect(Collectors.toList());
    }

}
