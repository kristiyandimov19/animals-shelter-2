package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.services.AnimalServiceModel;
import com.example.animalsshelter2.models.views.AnimalViewModel;
import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.services.AnimalService;
import com.example.animalsshelter2.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;

    private final UserService userService;

    public AnimalServiceImpl(AnimalRepository animalRepository, ModelMapper modelMapper, UserService userService) {
        this.animalRepository = animalRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void seedAnimals() {
        Animal animal1 = new Animal()
                .setName("Max")
                .setType("Dog")
                .setAvailability(true);

        Animal animal2 = new Animal()
                .setName("Sven")
                .setType("Sheep")
                .setAvailability(true);

        Animal animal3 = new Animal()
                .setName("Sparky")
                .setType("Dog")
                .setAvailability(true);

        animalRepository.saveAll(List.of(animal1, animal2, animal3));

    }

    @Override
    public List<AnimalViewModel> findAll() {
        return animalRepository.findAll()
                .stream()
                .map(a -> modelMapper.map(a, AnimalViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createAnimal(AnimalServiceModel animal) {
        Animal animalEntity = modelMapper.map(animal, Animal.class);

        User user = userService.findByName(animal.getUser());

        animalEntity.setUser(user);

        animalRepository.save(animalEntity);
    }

    @Override
    public void adoptAnimal(Long id) {
        animalRepository.deleteById(id);
    }
}
