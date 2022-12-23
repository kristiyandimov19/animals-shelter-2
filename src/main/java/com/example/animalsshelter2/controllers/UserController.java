package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.Comment;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.WalkHistory;
import com.example.animalsshelter2.models.request.CommentRequest;
import com.example.animalsshelter2.models.response.CommentResponse;
import com.example.animalsshelter2.models.response.UserAvailableResponse;
import com.example.animalsshelter2.models.response.WalkHistoryResponse;
import com.example.animalsshelter2.services.UserService;
import com.example.animalsshelter2.services.WalkHistoryService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
    public List<UserAvailableResponse> allAvailable() {
        return userService.findAllAvailable();
    }

    @GetMapping("/all")
    public List<UserAvailableResponse> findAllUsers() {
        try {
            return userService.findAllUsers();
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);

        }
    }

    @GetMapping("/{id}/comments")
    public List<CommentResponse> getUserComments(@PathVariable Long id) {
        try {
            User user = userService.findUserById(id);

            List<Comment> last5comments = user.getComments();

            if (user.getComments().size() > 5) {
                last5comments = user.getComments()
                        .subList(user.getComments().size() - 5, user.getComments().size());
            }

            Collections.reverse(last5comments);

            return last5comments
                    .stream().map(comment -> modelMapper.map(comment, CommentResponse.class)
                    )
                    .toList();
        }catch (EntityNotFoundException e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }
    @GetMapping("/{id}/walks")
    public List<WalkHistoryResponse> getWalkHistory(@PathVariable Long id) throws ParseException {
    try {
        List<WalkHistory> walkHistories = walkHistoryService.findByUserId(id);

        Collections.reverse(walkHistories);

        return walkHistories.stream()
                .map(walkHistory -> modelMapper.map(walkHistory, WalkHistoryResponse.class))
                .toList();
    }catch (EntityNotFoundException e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);

    }
    }

    @PutMapping("/{id}/takeAnimal/{animalId}")
    public void takeOnWalk(@PathVariable Long id, @PathVariable Long animalId) {
        try {
            userService.takeOnWalk(id, animalId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PutMapping("/{id}/returnAnimal/{animalId}")
    public void returnFromWalk(@PathVariable Long id, @PathVariable Long animalId) {

        userService.returnFromWalk(id, animalId);
    }

    @PutMapping("/comment/add")
    public void addComment(@RequestBody CommentRequest commentRequest) {
        userService.addComment(commentRequest.getAuthorId(),
                commentRequest.getUserId(),
                commentRequest.getDescription());
    }
}
