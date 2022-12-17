package com.example.animalsshelter2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.animalsshelter2.securitymodule"})
public class AnimalsShelter2Application {

    public static void main(String[] args) {
        SpringApplication.run(AnimalsShelter2Application.class, args);
    }

}
