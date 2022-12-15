package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.views.UserAvailableViewModel;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void seedUsers() {
        User user1 = new User()
                .setUsername("Admin")
                .setEmail("admin@admin.bg")
                .setPassword("asdasd")
                .setAdmin(true);

        User user2 = new User()
                .setUsername("User")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        userRepository.saveAll(List.of(user1, user2));
    }

    @Override
    public List<UserAvailableViewModel> findAllAvailable() {
        List<User> users = userRepository.findAllAvailable();

        return users.stream()
                .map(user -> modelMapper.map(user, UserAvailableViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAvailableViewModel> findAllUsers() {
        return userRepository.findAllUsers()
                .stream()
                .map(user -> modelMapper.map(user, UserAvailableViewModel.class))
                .collect(Collectors.toList());
    }
}
