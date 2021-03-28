package com.solomka.secondservice.config;

import com.solomka.secondservice.handlers.CustomResponseErrorHandler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class AppConfiguration {
    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplateBuilder()
                .errorHandler(new CustomResponseErrorHandler())
                .build();
    }
}
