package com.example.animalsshelter2.controllers;

import com.example.animalsshelter2.models.Comment;
import com.example.animalsshelter2.models.User;
import com.example.animalsshelter2.models.WalkHistory;
import com.example.animalsshelter2.models.request.CommentRequest;
import com.example.animalsshelter2.models.response.CommentResponse;
import com.example.animalsshelter2.models.response.UserAvailableResponse;
import com.example.animalsshelter2.models.response.WalkHistoryResponse;
import com.example.animalsshelter2.services.CommentService;
import com.example.animalsshelter2.services.UserService;
import com.example.animalsshelter2.services.WalkHistoryService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final WalkHistoryService walkHistoryService;
    private final CommentService commentService;

    public UserController(UserService userService, ModelMapper modelMapper, WalkHistoryService walkHistoryService, CommentService commentService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.walkHistoryService = walkHistoryService;

        this.commentService = commentService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/available")
    public List<UserAvailableResponse> allAvailable() {
        return userService.findAllAvailable();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public List<UserAvailableResponse> findAllUsers() {
        try {
            return userService.findAllUsers();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/check")
    public void checkValidToken(){}

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/comments")
    public List<CommentResponse> getUserComments(@PathVariable Long id) {
        return commentService.findAllByUserId(id);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/{id}/walks/page/{page}")
    public Page<WalkHistoryResponse> getWalkHistory(@PathVariable Long id, @PathVariable int page) throws ParseException {
        try {
            Page<WalkHistory> walkHistories = walkHistoryService.findByUserId(id, page);

            return walkHistories
                    .map(walkHistory -> modelMapper.map(walkHistory, WalkHistoryResponse.class));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/animal/{animalId}/walk")
    public void takeOnWalk(@PathVariable Long id, @PathVariable Long animalId) {
        try {
            userService.takeOnWalk(id, animalId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/animal/{animalId}/return")
    public void returnFromWalk(@PathVariable Long id, @PathVariable Long animalId) {

        userService.returnFromWalk(id, animalId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/comment")
    public void addComment(@RequestBody CommentRequest commentRequest) {
        commentService.createComment(commentRequest.getAuthorId(),
                commentRequest.getDescription(),
                commentRequest.getUserId());
    }
}
