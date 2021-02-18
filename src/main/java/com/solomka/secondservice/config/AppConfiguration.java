package com.solomka.secondservice.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class AppConfiguration {
    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
