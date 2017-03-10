package org.serc.agbot.scanner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.serc.agbot.SensorConfig;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.command.WaitContainerResultCallback;

public abstract class AbstractScanner {
    
    @Autowired
    protected SensorConfig sensorConfig;
    
    @Autowired
    protected DockerClient dockerClient;
    
    public String run(String input) throws IOException, InterruptedException {
        File dataDir = initData(input);
        String id = dockerClient.createContainerCmd(getImageName())
                .withBinds(new Bind(dataDir.getAbsolutePath(), getContainerDataVolumn()))
                .exec().getId();
        System.out.println("---------------");
        System.out.println(id);
        dockerClient.startContainerCmd(id).exec();
        System.out.println(dockerClient.waitContainerCmd(id).exec(new WaitContainerResultCallback()).awaitStatusCode());
        dockerClient.removeContainerCmd(id).withRemoveVolumes(true).exec();
        return getOutput(dataDir);
    }
    
    private File initData(String input) throws IOException {
        File dataDir = new File(sensorConfig.getDataDir(), getHostDataDirName());
        dataDir.mkdir();
        initInput(dataDir, input);
        return dataDir;
    }
    
    //第一个是host中的data.dir下相对地址，第二个是container下绝对地址
    protected abstract List<String> getDataDirPath();
    protected abstract String getImageName();
    
    protected void initInput(File dataDir, String input) throws IOException {
        FileUtils.writeStringToFile(new File(dataDir, getInputFileName()), input, "UTF-8");
    }
    
    protected String getInputFileName() {
        return "input";
    }
    
    protected String getOutputFileName() {
        return "output";
    }
    
    protected String getOutput(File dataDir) throws IOException {
        return FileUtils.readFileToString(new File(dataDir, getOutputFileName()), "UTF-8");
    }
    
    protected String getHostDataDirName() {
        return getDataDirPath().get(0);
    }
    
    protected Volume getContainerDataVolumn() {
        return new Volume(getDataDirPath().get(1));
    }
    
}
