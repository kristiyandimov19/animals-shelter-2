//package com.example.animalsshelter2.services.impl;
//
//import com.example.animalsshelter2.models.CustomUserDetails;
//import com.example.animalsshelter2.models.User;
//import com.example.animalsshelter2.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//public class CustomUserDetailsService implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user= userRepository.findByEmail(username);
//        if(user==null){
//            throw new UsernameNotFoundException("No such email.How hard is to remember your email....");
//        }
//        return new CustomUserDetails(user);
//
//    }
//}
