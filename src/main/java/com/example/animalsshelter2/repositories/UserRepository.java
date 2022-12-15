package com.example.animalsshelter2.repositories;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
