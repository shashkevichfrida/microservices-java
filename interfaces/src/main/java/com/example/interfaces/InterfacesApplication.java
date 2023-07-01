package com.example.interfaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example")
public class InterfacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterfacesApplication.class, args);
	}

}
