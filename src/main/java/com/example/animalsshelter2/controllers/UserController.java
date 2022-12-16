package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.Comment;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.services.CommentServiceModel;
import com.example.animalsshelter2.models.views.CommentViewModel;
import com.example.animalsshelter2.models.views.UserAvailableViewModel;
import com.example.animalsshelter2.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
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
        List<Comment> last5comments = user.getComments()
                .subList(user.getComments().size() - 5, user.getComments().size());

        List<CommentViewModel> commentViewModels = last5comments
                .stream().map(comment -> modelMapper.map(comment, CommentViewModel.class)
                ).toList();

        return commentViewModels;
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
