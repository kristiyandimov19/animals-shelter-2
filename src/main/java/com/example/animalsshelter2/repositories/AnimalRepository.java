package com.example.animalsshelter2.repositories;

import com.example.animalsshelter2.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
