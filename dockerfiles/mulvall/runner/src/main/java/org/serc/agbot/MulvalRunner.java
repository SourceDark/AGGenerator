package org.serc.agbot;

import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.serc.model.Graph;
import org.serc.model.Host;
import org.serc.model.Input;
import org.serc.model.Sensor;
import org.serc.model.attackgraph.AttackGraph;
import org.serc.model.attackgraph.AttackGraphFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@Component
public class MulvalRunner implements CommandLineRunner {
    
    private static final File BASE_DIR = new File("/data");
    

    @Override
    public void run(String... args) throws Exception {
        Runtime.getRuntime().exec("/root/startSql.bash", null, new File("/root")).waitFor();
        File tmpDir = new File(BASE_DIR, "tmp");
        tmpDir.mkdir();
        FileUtils.copyFile(new File("/input/config.txt"), new File(tmpDir, "config.txt"));
        Input input = JSON.parseObject(FileUtils.readFileToString(new File(BASE_DIR, "input"), "UTF-8"), Input.class);
        List<String> lines = Lists.newArrayList(input.getHacls());
        for(Sensor sensor : input.getSensors()) {
            for(Host host : sensor.getHosts()) {
                List<String> outputs = host2Input(sensor, host, tmpDir);
                if(outputs != null) {
                    lines.addAll(outputs);    
                }
            }
        }
        List<String> graphTxt = runMulval(lines, tmpDir);
        Graph graph = Mulval2Json.run(graphTxt, input.getHacls());
        AttackGraph attackGraph = AttackGraphFactory.toAttackGraph(graph, input.getHacls());
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter inputWriter= new StringWriter();
        objectMapper.writeValue(inputWriter, attackGraph);
        FileUtils.write(new File(BASE_DIR, "/output"), inputWriter.toString(), "UTF-8");
    }
    
    
    private List<String> host2Input(Sensor sensor, Host host, File tmpDir) throws Exception {
        File file = new File(tmpDir, "CVE.txt");
        String name = sensor.getName() + '_' + host.getIp().trim();
        name = name.replace("-", "_");
        name = name.replace(".", "_");
        List<String> lines = Lists.newArrayList(name, "CVE-2009-2446", "CVE-2004-0495");
        FileUtils.writeLines(file, lines);
        Runtime.getRuntime().exec("cve_translate.sh", null, tmpDir).waitFor();
        List<String> result = FileUtils.readLines(new File(tmpDir, "oval.P"), "UTF-8");
        return result.stream().filter(r -> r.startsWith("vul") || r.startsWith("cvss") || r.startsWith("network"))
                .collect(Collectors.toList());
    }
    
    private List<String> runMulval(List<String> lines, File tmpDir) throws Exception {
        FileUtils.writeLines(new File(tmpDir, "input.P"), lines);
        Runtime.getRuntime().exec("graph_gen.sh input.P -v  --nopdf", null, tmpDir).waitFor();
        File graphFile = new File(tmpDir, "AttackGraph.txt");
        if(graphFile.exists()) {
            return FileUtils.readLines(graphFile, "UTF-8");
        } else {
            return null;
        }
        
    }
    
}
