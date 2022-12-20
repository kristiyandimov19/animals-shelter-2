package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.config.JwtUtils;
import com.example.animalsshelter2.models.Comment;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.WalkHistory;
import com.example.animalsshelter2.models.services.CommentServiceModel;
import com.example.animalsshelter2.models.services.UserAuthServiceModel;
import com.example.animalsshelter2.models.views.CommentViewModel;
import com.example.animalsshelter2.models.views.UserAvailableViewModel;
import com.example.animalsshelter2.models.views.WalkHistoryViewModel;
import com.example.animalsshelter2.services.UserService;
import com.example.animalsshelter2.services.WalkHistoryService;
import org.modelmapper.ModelMapper;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final WalkHistoryService walkHistoryService;

    public UserController(UserService userService, ModelMapper modelMapper, WalkHistoryService walkHistoryService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.walkHistoryService = walkHistoryService;

    }

    @GetMapping("/available")
    public List<UserAvailableViewModel> allAvailable() {
        return userService.findAllAvailable();
    }

    @GetMapping("/all")
    public List<UserAvailableViewModel> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/comments/{id}")
    public List<CommentViewModel> getUserComments(@PathVariable Long id) {
        User user = userService.findUserById(id);

        List<Comment> last5comments = user.getComments();

        if (user.getComments().size() > 5) {
            last5comments = user.getComments()
                    .subList(user.getComments().size() - 5, user.getComments().size());
        }

        return last5comments
                .stream().map(comment -> modelMapper.map(comment, CommentViewModel.class)
                ).toList();
    }
    @GetMapping("/walks/{id}")
    public List<WalkHistoryViewModel> getWalkHistory(@PathVariable Long id) throws ParseException {

        List<WalkHistory> walkHistories = walkHistoryService.findByUserId(id);

        return walkHistories.stream()
                .map(walkHistory -> {
                    WalkHistoryViewModel walkHistoryViewModel = modelMapper.map(walkHistory, WalkHistoryViewModel.class);

                    walkHistoryViewModel.setAnimalName(walkHistory.getAnimal().getName());
                    walkHistoryViewModel.setAnimalType(walkHistory.getAnimal().getType());

                    return walkHistoryViewModel;

                })
                .toList();
    }

    @PutMapping("/takeOnWalk/{userId}/{animalId}")
    public void takeOnWalk(@PathVariable Long userId, @PathVariable Long animalId) {
        userService.takeOnWalk(userId, animalId);

    }

    @PutMapping("/returnFromWalk/{userId}/{animalId}")
    public void returnFromWalk(@PathVariable Long userId, @PathVariable Long animalId) {
        userService.returnFromWalk(userId, animalId);
    }

    @PutMapping("/comment/add")
    public void addComment(@RequestBody CommentServiceModel commentServiceModel) {
        userService.addComment(commentServiceModel.getAuthorId(),
                commentServiceModel.getUserId(),
                commentServiceModel.getDescription());
    }
}
