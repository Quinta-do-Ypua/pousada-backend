package com.senai.pousadabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.senai.pousadabackend.integration")
public class PousadaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PousadaBackendApplication.class, args);
    }

}
