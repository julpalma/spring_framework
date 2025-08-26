package com.example.demo.api;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.services.UserServiceInterface;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//API Layer
@Slf4j
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserServiceInterface userInterface;

    //dependency injection creates automatically an instance of the UserInterface class
    public UserController(UserServiceInterface userInterface) {
        this.userInterface = userInterface;
    }

    //Annotation @Valid triggers validation.
    //If invalid, Spring returns 400 Bad Request with error messages.
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userInterface.createUser(userRequest);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserResponse>> getUserById(@PathVariable("id") String userId) {
        Optional<UserResponse> userResponse = userInterface.getUserById(userId);

        if (userResponse.isPresent()) {
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") String id, @Valid @RequestBody UserUpdateRequest request) {
        try {
            UserResponse userResponse = userInterface.updateUser(request);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String userId) {
        try {
            userInterface.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
