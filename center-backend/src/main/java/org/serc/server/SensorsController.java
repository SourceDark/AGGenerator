package org.serc.server;

import java.util.List;
import java.util.stream.Collectors;

import org.serc.network.controller.dto.SensorListDto;
import org.serc.network.model.Network;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ServerSensorsController")
@RequestMapping("/server/{network}/sensors")
public class SensorsController {
    
    @GetMapping("")
    public List<SensorListDto> getSensors(Network network) {
        return network.getSensors().stream().map(SensorListDto::new).collect(Collectors.toList());
    }

}
