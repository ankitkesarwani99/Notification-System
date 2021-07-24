package com.notification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {
    @Value("${rest.template.connect.timeout}")
    private Integer restTemplateConnectTimeout;

    @Value("${rest.template.read.timeout}")
    private Integer restTemplateReadTimeout;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofMillis(restTemplateConnectTimeout)).setReadTimeout(Duration.ofMillis(restTemplateReadTimeout)).build();

    }
}
