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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("page/{page}")
    public Page<AnimalResponse> getAllAnimals(@PathVariable int page) {
        return animalService.findAll(page);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/volunteer")
    public UserIdResponse getVolunteerFor(@PathVariable Long id) {

        Long userId = animalService.findAnimalById(id).getUserId();
        return userService.findById(userId);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public void createAnimal(@RequestBody AnimalRequest animalRequest) {
        try {
            animalService.createAnimal(animalRequest);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Write it right !!!", e);


        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "All ready exist !!!", e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/isAvailable/{id}")
    public AnimalAvailableRequest isAvailable(@PathVariable Long id) {
        try {
            return animalService.findAnimalById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found !!!", e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/onWalk/page/{page}")
    public Page<AnimalWalkResponse> onWalk(@PathVariable int page) {
        try {
            return animalService.findAllAvailable(page);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/adopt/{id}")
    public void adoptAnimal(@PathVariable Long id) {
        try {
            animalService.adoptAnimal(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
    }
}
