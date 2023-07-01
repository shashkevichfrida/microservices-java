package com.example.externinteface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example")
public class ExternIntefaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternIntefaceApplication.class, args);
    }

}
