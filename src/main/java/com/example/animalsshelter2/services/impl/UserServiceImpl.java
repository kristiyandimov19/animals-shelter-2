package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.*;
import com.example.animalsshelter2.models.enums.UserRoleEnum;
import com.example.animalsshelter2.models.services.RegisterServiceModel;
import com.example.animalsshelter2.models.views.UserAvailableViewModel;
import com.example.animalsshelter2.models.views.UserIdViewModel;
import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.repositories.UserRoleRepository;
import com.example.animalsshelter2.repositories.WalkHistoryRepository;
import com.example.animalsshelter2.services.CommentService;
import com.example.animalsshelter2.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AnimalRepository animalRepository;
    private final CommentService commentService;
    private final WalkHistoryRepository walkHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           AnimalRepository animalRepository, CommentService commentService, WalkHistoryRepository walkHistoryRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.animalRepository = animalRepository;
        this.commentService = commentService;
        this.walkHistoryRepository = walkHistoryRepository;


        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
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
//
//    @Override
//    public void seedUsers() {
//        User user1 = new User()
//                .setUsername("Admin")
//                .setEmail("admin@admin.bg")
//                .setPassword("asdasd")
//                .setAdmin("admin");
//
//        User user2 = new User()
//                .setUsername("User")
//                .setEmail("user@user.bg")
//                .setPassword("asdasd")
//                .setAdmin("user");
//
//        User user3 = new User()
//                .setUsername("User2")
//                .setEmail("user@user.bg")
//                .setPassword("asdasd")
//                .setAdmin("user");
//
//        userRepository.saveAll(List.of(user1, user2, user3));
//    }


    @Override
    public List<UserAvailableViewModel> findAllAvailable() {
        UserRole userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        List<User> users = userRepository.findAllByAnimalIsNullAndRole(userRole);

        return users.stream()
                .map(user -> modelMapper.map(user, UserAvailableViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAvailableViewModel> findAllUsers() {
        UserRole userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        return userRepository.findAllByRole(userRole)
                .stream()
                .map(user -> modelMapper.map(user, UserAvailableViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void takeOnWalk(Long userId, Long animalId) {
        User user = userRepository.findById(userId).orElse(null);
        Animal animal = animalRepository.findById(animalId).orElse(null);

//        if (user.isAdmin() || !animal.isAvailability()) {
//            return;
//        }

        assert animal != null;
        animal.setAvailability(!animal.isAvailability());
        animal.setUser(user);
        assert user != null;
        user.setAnimal(animal);

        animalRepository.save(animal);
        userRepository.save(user);
    }

    @Override
    public void returnFromWalk(Long userId, Long animalId) {
        User user = userRepository.findById(userId).orElse(null);
        Animal animal = animalRepository.findById(animalId).orElse(null);
        WalkHistory walkHistory=new WalkHistory();
        walkHistory.setUser(user);
        walkHistory.setAnimal(animal);
        walkHistory.setLocalDate(LocalDate.now());

        assert animal != null;
        animal.setAvailability(!animal.isAvailability());
        animal.setUser(null);
        assert user != null;
        user.setAnimal(null);

        animalRepository.save(animal);
        userRepository.save(user);
        walkHistoryRepository.save(walkHistory);
    }

    @Override
    public void addComment(Long adminId, Long userId, String description) {
        User user = userRepository.findById(userId).orElse(null);

        Comment comment = commentService.createComment(adminId, description, userId);

        user.addComment(comment);

        userRepository.save(user);
    }


    @Override
    public void save(RegisterServiceModel newUser) {
        User user = modelMapper.map(newUser, User.class);
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        UserRole userRole = userRoleRepository.findById(2L).orElseThrow();
        user.setRole(userRole);
        userRepository.save(user);
    }


//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            throw new UsernameNotFoundException("No such user");
//
//        }
//        return new CustomUserDetails(user);
//    }


}
