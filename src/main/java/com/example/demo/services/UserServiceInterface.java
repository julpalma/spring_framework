package com.example.demo.services;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserUpdateRequest;

import java.util.Optional;

public interface UserServiceInterface {

    UserResponse createUser(UserRequest userRequest);

    Optional<UserResponse> getUserById(String userId);

    UserResponse updateUser(UserUpdateRequest request);

    void deleteUser(String userId);
}
