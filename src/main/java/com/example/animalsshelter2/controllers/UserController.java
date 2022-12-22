package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.config.JwtUtils;
import com.example.animalsshelter2.exceptions.UnauthorizedOperationException;
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
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.Collections;
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
        try {
            User user = userService.findUserById(id);

            List<Comment> last5comments = user.getComments();

            if (user.getComments().size() > 5) {
                last5comments = user.getComments()
                        .subList(user.getComments().size() - 5, user.getComments().size());
            }

            Collections.reverse(last5comments);

            return last5comments
                    .stream().map(comment -> modelMapper.map(comment, CommentViewModel.class)
                    )
                    .toList();
        }catch (EntityNotFoundException e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/walks/{id}")
    public List<WalkHistoryViewModel> getWalkHistory(@PathVariable Long id) throws ParseException {

        List<WalkHistory> walkHistories = walkHistoryService.findByUserId(id);

        Collections.reverse(walkHistories);

        return walkHistories.stream()
                .map(walkHistory -> modelMapper.map(walkHistory, WalkHistoryViewModel.class))
                .toList();
    }

    @PutMapping("/takeOnWalk/{userId}/{animalId}")
    public void takeOnWalk(@PathVariable Long userId, @PathVariable Long animalId) {
        try {
            userService.takeOnWalk(userId, animalId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

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
