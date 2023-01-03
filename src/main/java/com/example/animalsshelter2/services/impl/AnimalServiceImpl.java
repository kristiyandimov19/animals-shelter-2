package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.response.AnimalAvailableResponse;
import com.example.animalsshelter2.models.request.AnimalRequest;
import com.example.animalsshelter2.models.response.AnimalResponse;
import com.example.animalsshelter2.models.response.AnimalWalkResponse;
import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.AnimalService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

        if (animalRepository.count() != 0) {
            return;
        }

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
    public Page<AnimalResponse> findAll(int page) {

        PageRequest requestPage = PageRequest.of(page, 6);

        return animalRepository.findAll(requestPage)
                .map(a -> modelMapper.map(a, AnimalResponse.class));
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
    public AnimalAvailableResponse findAnimalById(Long id) {
        try {
            Animal animal = animalRepository.findById(id).orElseThrow();
            return modelMapper.map(animal, AnimalAvailableResponse.class);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found", e);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No accreditation !!!", e);
        }

    }


    @Override
    public Page<AnimalWalkResponse> findAllAvailable(int page) {

        PageRequest requestPage = PageRequest.of(page, 6);

        Page<Animal> animals = animalRepository.findAllAvailable(requestPage);

        return animals
                .map(animal -> {
                    AnimalWalkResponse animalWalkResponse = modelMapper
                            .map(animal, AnimalWalkResponse.class);

                    User user = userRepository.findById(animal.getUser().getId()).orElse(null);
                    if (user == null) {
                        return animalWalkResponse;
                    }
                    animalWalkResponse.setUsername(user.getUsername());
                    return animalWalkResponse;
                });
    }
}
