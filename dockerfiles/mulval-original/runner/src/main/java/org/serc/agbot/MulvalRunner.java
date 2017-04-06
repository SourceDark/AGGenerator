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
        Runtime.getRuntime().exec("/root/startSql.bash", null, new File("/root")).waitFor();
        File tmpDir = new File(BASE_DIR, "tmp");
        tmpDir.mkdir();
        FileUtils.copyFile(new File("/input/config.txt"), new File(tmpDir, "config.txt"));
        String input = FileUtils.readFileToString(new File(BASE_DIR, "input"), "UTF-8");
        List<String> graphTxt = runMulval(input, tmpDir);
        Graph graph = Mulval2Json.run(graphTxt, input);
        AttackGraph attackGraph = AttackGraphFactory.toAttackGraph(graph, input);
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter inputWriter= new StringWriter();
        objectMapper.writeValue(inputWriter, attackGraph);
        FileUtils.write(new File(BASE_DIR, "/output"), inputWriter.toString(), "UTF-8");
    }
    
    private List<String> runMulval(String lines, File tmpDir) throws Exception {
        FileUtils.write(new File(tmpDir, "input.P"), lines, "utf-8");
        Runtime.getRuntime().exec("graph_gen.sh input.P -v", null, tmpDir).waitFor();
        File graphFile = new File(tmpDir, "AttackGraph.txt");
        if(graphFile.exists()) {
            return FileUtils.readLines(graphFile, "UTF-8");
        } else {
            return null;
        }
    }
    
}
