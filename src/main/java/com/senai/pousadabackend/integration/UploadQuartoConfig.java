package com.senai.pousadabackend.integration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class UploadQuartoConfig {

    @Value("${imagekit.chavePrivada}")
    private String chavePrivada;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String auth = chavePrivada + ":";
            String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
            requestTemplate.header("Authorization", "Basic " + encoded);
        };
    }
}
