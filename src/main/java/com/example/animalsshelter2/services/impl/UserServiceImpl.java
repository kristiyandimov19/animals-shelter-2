package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.config.JwtUtils;
import com.example.animalsshelter2.models.*;
import com.example.animalsshelter2.models.enums.UserRoleEnum;
import com.example.animalsshelter2.models.request.LoginRequest;
import com.example.animalsshelter2.models.request.RegisterRequest;
import com.example.animalsshelter2.models.response.LoginResponse;
import com.example.animalsshelter2.models.response.RegistrationResponse;
import com.example.animalsshelter2.models.response.UserAvailableResponse;
import com.example.animalsshelter2.models.response.UserIdResponse;
import com.example.animalsshelter2.repositories.AnimalRepository;
import com.example.animalsshelter2.repositories.UserRepository;
import com.example.animalsshelter2.repositories.UserRoleRepository;
import com.example.animalsshelter2.repositories.WalkHistoryRepository;
import com.example.animalsshelter2.services.CommentService;
import com.example.animalsshelter2.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
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
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           AnimalRepository animalRepository, CommentService commentService, WalkHistoryRepository walkHistoryRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.animalRepository = animalRepository;
        this.commentService = commentService;
        this.walkHistoryRepository = walkHistoryRepository;


        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
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
    public UserIdResponse findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return modelMapper.map(user, UserIdResponse.class);
    }

    @Override
    public void seedUsers() {

        if (userRepository.count() != 0) {
            return;
        }

        UserRole adminRole = new UserRole().setRole(UserRoleEnum.ADMIN);
        UserRole userRole = new UserRole().setRole(UserRoleEnum.USER);

        userRoleRepository.saveAll(List.of(adminRole, userRole));

        for (int i = 1; i <= 5; i++) {
            userRepository.save(new User()
                    .setUsername("Admin" + i)
                    .setEmail("admin" + i + "@admin.bg")
                    .setPassword(passwordEncoder.encode("asdasd"))
                    .setRole(adminRole));
        }

        for (int i = 1; i <= 30; i++) {
            userRepository.save(new User()
                    .setUsername("User" + i)
                    .setEmail("user" + i + "@user.bg")
                    .setPassword(passwordEncoder.encode("asdasd"))
                    .setRole(userRole));
        }
    }


    @Override
    public List<UserAvailableResponse> findAllAvailable() {
        UserRole userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        List<User> users = userRepository.findAllByAnimalIsNullAndRole(userRole);

        return users.stream()
                .map(user -> modelMapper.map(user, UserAvailableResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAvailableResponse> findAllUsers() {
        UserRole userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        return userRepository.findAllByRole(userRole)
                .stream()
                .map(user -> modelMapper.map(user, UserAvailableResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void takeOnWalk(Long userId, Long animalId) throws Exception {
        if (userRepository.findById(userId).isPresent() &&
                animalRepository.findById(animalId).isPresent()) {
            User user = userRepository.findById(userId).get();
            Animal animal = animalRepository.findById(animalId).get();
            if (user.getAnimal() != null || !animal.isAvailability()) {
                throw new Exception("Someone is occupied");
            }

            animal.setAvailability(!animal.isAvailability());
            animal.setUser(user);
            user.setAnimal(animal);

            animalRepository.save(animal);
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found.");
        }

    }

    @Override
    public void returnFromWalk(Long userId, Long animalId) {
        if (userRepository.findById(userId).isPresent() &&
                animalRepository.findById(animalId).isPresent()) {
            User user = userRepository.findById(userId).get();
            Animal animal = animalRepository.findById(animalId).get();
            WalkHistory walkHistory = new WalkHistory();
            walkHistory.setUser(user);
            walkHistory.setAnimalName(animal.getName());
            walkHistory.setAnimalType(animal.getType());
            walkHistory.setLocalDate(LocalDate.now());

            animal.setAvailability(!animal.isAvailability());
            animal.setUser(null);
            user.setAnimal(null);

            animalRepository.save(animal);
            userRepository.save(user);
            walkHistoryRepository.save(walkHistory);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found.");
        }

    }

    @Override
    public RegistrationResponse save(RegisterRequest newUser) {
        try {
            User user = modelMapper.map(newUser, User.class);
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            UserRole userRole = userRoleRepository.findByRole(UserRoleEnum.USER);
            user.setRole(userRole);
            userRepository.save(user);
            String token = jwtUtils.generateToken(user.getRole(), user.getEmail(), user.getId());
            RegistrationResponse response = new RegistrationResponse();
            response.setToken(token);
            return response;
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Write it right !!!", e);
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword());

            authenticationManager.authenticate(authenticationToken);

            User user = userRepository.findByEmail(loginRequest.getEmail());
            String token = jwtUtils.generateToken(user.getRole(), user.getEmail(), user.getId());
            return new LoginResponse().setToken(token);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Login Credentials", e);
        }
    }


}
