package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.request.AnimalAvailableRequest;
import com.example.animalsshelter2.models.request.AnimalRequest;
import com.example.animalsshelter2.models.response.AnimalResponse;
import com.example.animalsshelter2.models.response.AnimalWalkResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AnimalService {
    //void seedAnimals();

    Page<AnimalResponse> findAll(int page);

    void createAnimal(AnimalRequest animal);

    void adoptAnimal(Long id);

    AnimalAvailableRequest findAnimalById(Long id);


    Page<AnimalWalkResponse> findAllAvailable(int page);

}
