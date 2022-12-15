package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.services.AnimalAvailableServiceModel;
import com.example.animalsshelter2.models.services.AnimalServiceModel;
import com.example.animalsshelter2.models.views.AnimalViewModel;

import java.util.List;

public interface AnimalService {
    void seedAnimals();

    List<AnimalViewModel> findAll();

    void createAnimal(AnimalServiceModel animal);

    void adoptAnimal(Long id);

    AnimalAvailableServiceModel findAnimalById(Long id);
}
