package com.example.carmodelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example")
public class CarModelServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarModelServiceApplication.class, args);
    }

}
