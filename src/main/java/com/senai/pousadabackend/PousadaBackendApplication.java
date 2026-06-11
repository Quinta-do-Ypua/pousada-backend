package com.senai.pousadabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = {"com.senai.pousadabackend.domain", "com.senai.pousadabackend.infraestructure"})
public class PousadaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PousadaBackendApplication.class, args);
    }

}
