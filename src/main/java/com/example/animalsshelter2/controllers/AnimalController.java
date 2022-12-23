package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.services.AnimalAvailableServiceModel;
import com.example.animalsshelter2.models.services.AnimalServiceModel;
import com.example.animalsshelter2.models.views.AnimalViewModel;
import com.example.animalsshelter2.models.views.AnimalWalkViewModel;
import com.example.animalsshelter2.models.views.UserIdViewModel;
import com.example.animalsshelter2.services.AnimalService;
import com.example.animalsshelter2.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;
    private final UserService userService;

    public AnimalController(AnimalService animalService, UserService userService) {
        this.animalService = animalService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<AnimalViewModel> getAllAnimals() {
        return animalService.findAll();
    }

    @GetMapping("/volunteer/{id}")
    public UserIdViewModel getVolunteer(@PathVariable Long id) {
        try{
            Long userId = animalService.findAnimalById(id).getUserId();
            return userService.findById(userId);

        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found",e);

        }catch (HttpClientErrorException.Unauthorized e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No accreditation !!!",e);
        }


    }


    @PostMapping("/create")
    public void createAnimal(@RequestBody AnimalServiceModel animalServiceModel) {
        try {
            animalService.createAnimal(animalServiceModel);
        }catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write it right !!!", e);


        }catch (DuplicateKeyException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"All ready exist !!!",e);
        }
    }

    @GetMapping("/isAvailable/{id}")
    public AnimalAvailableServiceModel isAvailable(@PathVariable Long id) {
    try {
        return animalService.findAnimalById(id);
    }catch (EntityNotFoundException e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found !!!",e);
    }
    }

    @GetMapping("/onWalk")

    public List<AnimalWalkViewModel> onWalk() {
    try{
        return animalService.findAllAvailable();
    }catch (IllegalArgumentException e){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    }

    @DeleteMapping("/delete/{id}")
    public void adoptAnimal(@PathVariable Long id) {
        try {
            animalService.adoptAnimal(id);
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
    }
}
