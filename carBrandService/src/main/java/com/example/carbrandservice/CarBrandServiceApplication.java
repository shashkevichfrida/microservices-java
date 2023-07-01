package com.example.carbrandservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example")
public class CarBrandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarBrandServiceApplication.class, args);
    }

}
