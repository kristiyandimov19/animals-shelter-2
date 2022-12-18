package com.example.animalsshelter2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.example.animalsshelter2.models")
public class AnimalsShelter2Application {

    public static void main(String[] args) {
        SpringApplication.run(AnimalsShelter2Application.class, args);
    }

}
