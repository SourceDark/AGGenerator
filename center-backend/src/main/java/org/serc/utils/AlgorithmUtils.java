package org.serc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;

@Component
public class AlgorithmUtils {
    
    private static DockerClient dockerClient;
    
    @Autowired
    public void setDockerClient(DockerClient dockerClient) {
        AlgorithmUtils.dockerClient = dockerClient;
    }

    public static String getErrorStackString(Exception e) {
        String errorStack = e.getMessage();
        for(StackTraceElement element : e.getStackTrace()){
            errorStack += element.toString();
        }
        return errorStack;
    }
    
    public static String getLog(String containerId) {
        StringBufferLogReader loggingCallback = new StringBufferLogReader();
        dockerClient.logContainerCmd(containerId).withStdErr(true).withStdOut(true).exec(loggingCallback);
        try {
            loggingCallback.awaitCompletion();
        } catch (InterruptedException e) {
            return "";
        }
        return loggingCallback.toString();
    }
    
    public static class StringBufferLogReader extends LogContainerResultCallback {
        protected final StringBuffer log = new StringBuffer();

        @Override
        public void onNext(Frame frame) {
            log.append(new String(frame.getPayload()));
            super.onNext(frame);
        }

        @Override
        public String toString() {
            return log.toString();
        }
    }

}
