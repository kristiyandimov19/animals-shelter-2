package com.example.animalsshelter2.security;

import com.example.animalsshelter2.models.UserRole;
import org.springframework.security.core.GrantedAuthority;


public class SecurityRoles implements GrantedAuthority {

    private final UserRole role;

    public SecurityRoles(UserRole role) {
        this.role = role;
    }

    public String getRole() {
        return role.getRole().toString();
    }

    @Override
    public String getAuthority() {
        return role.getRole().toString();
    }
}
