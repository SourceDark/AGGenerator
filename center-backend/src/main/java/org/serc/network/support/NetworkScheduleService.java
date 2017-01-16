package org.serc.network.support;

import java.io.StringWriter;

import org.serc.network.model.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

@Component
public class NetworkScheduleService {
    
    @Autowired NetworkRepository networkRepository;
    @Autowired ObjectMapper objectMapper;
    
    @Scheduled(cron = "0 2 0 * * ?")
    public void networkSchedule() throws Exception {
        for(Network network: networkRepository.findAll()) {
            StringWriter inputWriter= new StringWriter();
            objectMapper.writeValue(inputWriter, ImmutableMap.of("sensors", network.getSensors(), "hacls", network.getHacls()));
            String input = inputWriter.toString();
        }
    }

}
