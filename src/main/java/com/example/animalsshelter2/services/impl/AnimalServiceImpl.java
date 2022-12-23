package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.request.AnimalAvailableRequest;
import com.example.animalsshelter2.models.request.AnimalRequest;
import com.example.animalsshelter2.models.response.AnimalResponse;
import com.example.animalsshelter2.models.response.AnimalWalkResponse;
import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.AnimalService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

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
    public List<AnimalResponse> findAll() {
        return animalRepository.findAll()
                .stream()
                .map(a -> modelMapper.map(a, AnimalResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createAnimal(AnimalRequest animal) {
        Animal animalEntity = modelMapper.map(animal, Animal.class);

        animalRepository.save(animalEntity);
    }

    @Override
    public void adoptAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    @Override
    public AnimalAvailableRequest findAnimalById(Long id) {
        try {
            Animal animal = animalRepository.findById(id).orElseThrow();
            return modelMapper.map(animal, AnimalAvailableRequest.class);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found", e);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No accreditation !!!", e);
        }

    }


    @Override
    public List<AnimalWalkResponse> findAllAvailable() {
        List<Animal> animals = animalRepository.findAllAvailable();

        return animals
                .stream()
                .map(animal -> {
                    AnimalWalkResponse animalWalkResponse = modelMapper
                            .map(animal, AnimalWalkResponse.class);

                    User user = userRepository.findById(animal.getUser().getId()).orElse(null);
                    if (user == null) {
                        return animalWalkResponse;
                    }
                    animalWalkResponse.setUsername(user.getUsername());
                    return animalWalkResponse;
                })
                .collect(Collectors.toList());
    }
}
