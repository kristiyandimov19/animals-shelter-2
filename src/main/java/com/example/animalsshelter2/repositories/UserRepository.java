package com.example.animalsshelter2.repositories;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User  findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role =:admin")
    List<User> findAllUsers();

    @Query("SELECT u FROM User u WHERE u.animal IS NULL AND u.role =:admin")
    List<User> findAllAvailable();
}
