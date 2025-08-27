package com.example.demo.services;

import com.example.demo.dto.*;

import java.util.Optional;

public interface UserServiceInterface {

    UserResponse createUser(UserRequest userRequest);

    Optional<UserResponse> getUserById(String userId);

    Optional<UserCredentialsResponse> getUserCredentials(String userId);

    UserResponse updateUser(String id, UserUpdateRequest request, String username);

    UserResponse updateUserCredentials(UserCredentialsUpdateRequest request);

    void deleteUser(String userId);
}
