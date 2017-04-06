package org.serc.agbot;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.serc.model.Graph;
import org.serc.model.attackgraph.AttackGraph;
import org.serc.model.attackgraph.AttackGraphFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MulvalRunner implements CommandLineRunner {
    
    private static final File BASE_DIR = new File("/data");
    

    @Override
    public void run(String... args) throws Exception {
        System.out.println("1111");
        String input = FileUtils.readFileToString(new File(BASE_DIR, "input"), "UTF-8");
        List<String> output = FileUtils.readLines(new File(BASE_DIR, "output"), "UTF-8");
        System.out.println("2222");
        Graph graph = Mulval2Json.run(output, input);
        System.out.println("3333");
        AttackGraph attackGraph = AttackGraphFactory.toAttackGraph(graph, input);
        System.out.println("4444");
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter inputWriter= new StringWriter();
        objectMapper.writeValue(inputWriter, attackGraph);
        System.out.println("5555");
        FileUtils.write(new File(BASE_DIR, "/output"), inputWriter.toString(), "UTF-8");
        System.out.println("6666");
    }
    
}
