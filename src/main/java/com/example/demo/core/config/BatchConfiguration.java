package com.example.demo.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {

    @Bean
    BatchExitCodeGenerator exitCodeGenerator() {
        return new BatchExitCodeGenerator();
    }
}
