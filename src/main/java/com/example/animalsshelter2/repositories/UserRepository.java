package com.example.animalsshelter2.repositories;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.isAdmin = false")
    List<User> findAllUsers();

    @Query("SELECT u FROM User u WHERE u.animal IS NULL AND u.isAdmin = false")
    List<User> findAllAvailable();
}
