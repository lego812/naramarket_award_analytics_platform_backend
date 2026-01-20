package com.example.award.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${award.api.base-url}")
    private String baseUrl;

    @Bean
    public RestClient restTemplate() {
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}
