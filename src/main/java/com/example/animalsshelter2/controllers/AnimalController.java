package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.services.AnimalAvailableServiceModel;
import com.example.animalsshelter2.models.services.AnimalServiceModel;
import com.example.animalsshelter2.models.views.AnimalViewModel;
import com.example.animalsshelter2.models.views.AnimalWalkViewModel;
import com.example.animalsshelter2.models.views.UserIdViewModel;
import com.example.animalsshelter2.services.AnimalService;
import com.example.animalsshelter2.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AnimalController(AnimalService animalService, UserService userService, ModelMapper modelMapper) {
        this.animalService = animalService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<AnimalViewModel> getAllAnimals() {
        return animalService.findAll();
    }

    @GetMapping("/volunteer/{id}")
    public UserIdViewModel getVolunteer(@PathVariable Long id) {
        Long userId = animalService.findAnimalById(id).getUserId();
        return userService.findById(userId);
    }



    @PostMapping("/create")
    public void createAnimal(@RequestBody AnimalServiceModel animalServiceModel) {

        animalService.createAnimal(animalServiceModel);
    }

    @GetMapping("/isAvailable/{id}")
    public AnimalAvailableServiceModel isAvailable(@PathVariable Long id) {

        return animalService.findAnimalById(id);
    }

    @GetMapping("/onWalk")
    public List<AnimalWalkViewModel> onWalk() {
        return animalService.findAllAvailable();
    }

    @DeleteMapping("/delete/{id}")
    public void adoptAnimal(@PathVariable Long id) {
        animalService.adoptAnimal(id);
    }
}
