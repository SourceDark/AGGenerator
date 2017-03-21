package org.serc.agbot.scanner;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.serc.agbot.SensorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ScannerRunner implements CommandLineRunner {
    
    @Autowired
    private VulnerabilityScanner vulnerabilityScanner;
    
    @Autowired
    private SensorConfig sensorConfig;

    @Override
    public void run(String... args) throws Exception {
        String input = "";
        for(String ip : sensorConfig.getIps()) {
            input += ip + "\n";
        }
        String vulnerabilityOutput = vulnerabilityScanner.run(input);
        FileUtils.writeStringToFile(new File(sensorConfig.getDataDir(), "output"), vulnerabilityOutput, "utf-8");
    }
    
}
