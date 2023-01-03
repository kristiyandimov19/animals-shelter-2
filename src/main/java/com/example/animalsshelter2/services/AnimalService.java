package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.response.AnimalAvailableResponse;
import com.example.animalsshelter2.models.request.AnimalRequest;
import com.example.animalsshelter2.models.response.AnimalResponse;
import com.example.animalsshelter2.models.response.AnimalWalkResponse;
import org.springframework.data.domain.Page;

public interface AnimalService {
    void seedAnimals();

    Page<AnimalResponse> findAll(int page);

    void createAnimal(AnimalRequest animal);

    void adoptAnimal(Long id);

    AnimalAvailableResponse findAnimalById(Long id);


    Page<AnimalWalkResponse> findAllAvailable(int page);

}
