package org.serc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

@Configuration
public class DockerConfiguration {
    
    @Autowired
    ApplicationContext applicationContext;
    
    @Bean
    public DockerClient getDockerClient() {
        return DockerClientBuilder.getInstance(DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(ApplicationContext.dockerHost)).build();
    }

}
