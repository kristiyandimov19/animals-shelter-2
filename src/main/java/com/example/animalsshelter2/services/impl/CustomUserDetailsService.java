package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("No such email.How hard is to remember your email....");
        }
        return new SecurityUser(user);

    }


}
