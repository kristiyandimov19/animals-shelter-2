package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.request.AnimalAvailableRequest;
import com.example.animalsshelter2.models.request.AnimalRequest;
import com.example.animalsshelter2.models.response.AnimalResponse;
import com.example.animalsshelter2.models.response.AnimalWalkResponse;

import java.util.List;

public interface AnimalService {
    void seedAnimals();

    List<AnimalResponse> findAll();

    void createAnimal(AnimalRequest animal);

    void adoptAnimal(Long id);

    AnimalAvailableRequest findAnimalById(Long id);


    List<AnimalWalkResponse> findAllAvailable();

}
