package com.example.animalsshelter2.models;

import com.example.animalsshelter2.models.enums.UserRoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public UserRole() {
    }

    public UserRole(UserRoleEnum role) {
        this.role = role;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public UserRole setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
