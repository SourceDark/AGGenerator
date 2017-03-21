package org.serc;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContext {
    
    public static Boolean debug;
    
    public static String dataDir;
    public static String hostDataDir;
    public static String hostTmpDir;
    public static String dockerHost;
    public static String dockerPort;
    
    public static String centerApi;
    public static String cveApi;
    
    public static File getDataDir() {
        return new File(dataDir);
    }
    
    public static String toHostDir(File file) {
        String absolutePath = file.getAbsolutePath();
        return absolutePath.replace(dataDir, hostDataDir);
    }
    
    @Value("${data.dir}")
    public void setDataDir(String dataDir) {
        ApplicationContext.dataDir = dataDir;
    }
    
    @Value("${host.data.dir}")
    public void setHostDataDir(String dataDir) {
        ApplicationContext.hostDataDir = dataDir;
    }
    
    @Value("${host.tmp.dir:/tmp}")
    public void setHostTmpDir(String tmpDir) {
        ApplicationContext.hostTmpDir = tmpDir;
    }
    
    @Value("${docker.host}")
    public void setDockerHost(String dockerHost) {
        ApplicationContext.dockerHost = dockerHost;
    }

    @Value("${docker.port}")
    public void setDockerPort(String dockerPort) {
        ApplicationContext.dockerPort = dockerPort;
    }

    @Value("${data.center.api}")
    public void setCenterApi(String centerApi) {
        ApplicationContext.centerApi = centerApi;
    }
    
    @Value("${data.cve.api}")
    public void setCveApi(String cveApi) {
        ApplicationContext.cveApi = cveApi;
    }
    
    @Value("${debug}")
    public void setDebug(Boolean debug) {
        ApplicationContext.debug = debug;
    }

}
