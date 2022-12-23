package com.example.animalsshelter2.repositories;

import com.example.animalsshelter2.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a WHERE a.availability = false")
    List<Animal> findAllAvailable();
}
