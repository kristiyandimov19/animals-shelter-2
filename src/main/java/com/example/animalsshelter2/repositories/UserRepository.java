package com.example.animalsshelter2.repositories;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User  findByEmail(String email);

    List<User> findAllByRole(UserRole role);

    List<User> findAllByAnimalIsNullAndRole(UserRole userRole);
}
