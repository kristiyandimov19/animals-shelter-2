package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.services.AnimalAvailableServiceModel;
import com.example.animalsshelter2.models.services.AnimalServiceModel;
import com.example.animalsshelter2.models.views.AnimalViewModel;
import com.example.animalsshelter2.models.views.AnimalWalkViewModel;
import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.AnimalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.animalRepository = animalRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
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
                .setName("Dolly")
                .setType("Dragon")
                .setAvailability(true);

        Animal animal4 = new Animal()
                .setName("Kodi")
                .setType("Cat")
                .setAvailability(true);

        Animal animal5 = new Animal()
                .setName("Enrique")
                .setType("Cow")
                .setAvailability(true);

        animalRepository.saveAll(List.of(animal1, animal2, animal3, animal4, animal5));

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

        animalRepository.save(animalEntity);
    }

    @Override
    public void adoptAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    @Override
    public AnimalAvailableServiceModel findAnimalById(Long id) {
        Animal animal = animalRepository.findById(id).orElse(null);
        return modelMapper.map(animal, AnimalAvailableServiceModel.class);
    }


    @Override
    public List<AnimalWalkViewModel> findAllAvailable() {
        List<Animal> animals = animalRepository.findAllAvailable();

        return animals
                .stream()
                .map(animal -> {
                    AnimalWalkViewModel animalWalkViewModel = modelMapper
                            .map(animal, AnimalWalkViewModel.class);

                    User user = userRepository.findById(animal.getUser().getId()).orElse(null);
                    if (user == null) {
                        return animalWalkViewModel;
                    }
                    animalWalkViewModel.setUsername(user.getUsername());
                    return animalWalkViewModel;
                })
                .collect(Collectors.toList());
    }
}
