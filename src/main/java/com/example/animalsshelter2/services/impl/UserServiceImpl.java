package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.Animal;
import com.example.animalsshelter2.models.Comment;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.views.UserAvailableViewModel;
import com.example.animalsshelter2.models.views.UserIdViewModel;
import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.services.CommentService;
import com.example.animalsshelter2.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AnimalRepository animalRepository;
    private final CommentService commentService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           AnimalRepository animalRepository, CommentService commentService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.animalRepository = animalRepository;
        this.commentService = commentService;
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserIdViewModel findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return modelMapper.map(user, UserIdViewModel.class);
    }

    @Override
    public void seedUsers() {
        User user1 = new User()
                .setUsername("Admin")
                .setEmail("admin@admin.bg")
                .setPassword("asdasd")
                .setAdmin(true);

        User user2 = new User()
                .setUsername("User1")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user3 = new User()
                .setUsername("User2")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user4 = new User()
                .setUsername("User3")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user5 = new User()
                .setUsername("User4")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user6 = new User()
                .setUsername("User5")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user7 = new User()
                .setUsername("User6")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user8 = new User()
                .setUsername("User7")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user9 = new User()
                .setUsername("User8")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user10 = new User()
                .setUsername("User9")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user11 = new User()
                .setUsername("User10")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user12 = new User()
                .setUsername("User11")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user13 = new User()
                .setUsername("User12")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        User user14 = new User()
                .setUsername("User13")
                .setEmail("user@user.bg")
                .setPassword("asdasd")
                .setAdmin(false);

        userRepository.saveAll(List.of(user1, user2,user3,user4,user5, user6,user7,user8,user9,user10,user11, user12,user13,user14));
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

    @Override
    public void takeOnWalk(Long userId, Long animalId) {
        User user = userRepository.findById(userId).orElse(null);
        Animal animal = animalRepository.findById(animalId).orElse(null);

        if (user.isAdmin()) {
            return;
        }

        animal.setAvailability(!animal.isAvailability());
        animal.setUser(user);
        user.setAnimal(animal);

        animalRepository.save(animal);
        userRepository.save(user);
    }

    @Override
    public void returnFromWalk(Long userId, Long animalId) {
        User user = userRepository.findById(userId).orElse(null);
        Animal animal = animalRepository.findById(animalId).orElse(null);

        animal.setAvailability(!animal.isAvailability());
        animal.setUser(null);
        user.setAnimal(null);

        animalRepository.save(animal);
        userRepository.save(user);
    }

    @Override
    public void addComment(Long adminId, Long userId, String description) {
        User user = userRepository.findById(userId).orElse(null);

        Comment comment = commentService.createComment(adminId, description, userId);

        user.addComment(comment);

        userRepository.save(user);
    }


}
