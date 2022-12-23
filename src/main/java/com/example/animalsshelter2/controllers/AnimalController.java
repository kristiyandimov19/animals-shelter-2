package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.request.AnimalAvailableRequest;
import com.example.animalsshelter2.models.request.AnimalRequest;
import com.example.animalsshelter2.models.response.AnimalResponse;
import com.example.animalsshelter2.models.response.AnimalWalkResponse;
import com.example.animalsshelter2.models.response.UserIdResponse;
import com.example.animalsshelter2.services.AnimalService;
import com.example.animalsshelter2.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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

    @GetMapping()
    public List<AnimalResponse> getAllAnimals() {
        return animalService.findAll();
    }

    @GetMapping("/{id}/volunteer")
    public UserIdResponse getVolunteerFor(@PathVariable Long id) {
        try{
            Long userId = animalService.findAnimalById(id).getUserId();
            return userService.findById(userId);

        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found",e);

        }catch (HttpClientErrorException.Unauthorized e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No accreditation !!!",e);
        }


    }


    @PostMapping()
    public void createAnimal(@RequestBody AnimalRequest animalRequest) {
        try {
            animalService.createAnimal(animalRequest);
        }catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write it right !!!", e);


        }catch (DuplicateKeyException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"All ready exist !!!",e);
        }
    }

    @GetMapping("/isAvailable/{id}")
    public AnimalAvailableRequest isAvailable(@PathVariable Long id) {
    try {
        return animalService.findAnimalById(id);
    }catch (EntityNotFoundException e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found !!!",e);
    }
    }

    @GetMapping("/onWalk")

    public List<AnimalWalkResponse> onWalk() {
    try{
        return animalService.findAllAvailable();
    }catch (IllegalArgumentException e){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    }

    @DeleteMapping("/adopt/{id}")
    public void adoptAnimal(@PathVariable Long id) {
        try {
            animalService.adoptAnimal(id);
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
    }
}
