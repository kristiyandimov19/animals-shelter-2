package com.example.animalsshelter2.repositories;

import com.example.animalsshelter2.models.UserRole;
import com.example.animalsshelter2.models.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByRole(UserRoleEnum role);
}
