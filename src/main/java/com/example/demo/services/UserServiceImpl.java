package com.example.demo.services;

import com.example.demo.dto.*;
import com.example.demo.enums.Country;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.UserCredentials;
import com.example.demo.repository.UserCredentialsRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

//Service Layer (Business logic)
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInterface {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .address(userRequest.getAddress())
                .country(Country.valueOf(userRequest.getCountry()))
                .build();

        User savedUser = userRepository.save(user);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(userRequest.getPassword());

        //Create credentials and link to the user
        UserCredentials userCredentials = UserCredentials.builder()
                .username(userRequest.getUsername())
                .password(hashedPassword)
                .user(savedUser).build();

        userCredentialsRepository.save(userCredentials);

        log.info("User saved: {}", savedUser);

        // Map to response DTO
        return UserResponse.builder()
                .id(savedUser.getId().toString())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .address(savedUser.getAddress())
                .country(String.valueOf(savedUser.getCountry()))
                .username(userCredentials.getUsername())
                .build();
    }

    @Override
    public Optional<UserResponse> getUserById(String userId) {
        Optional<User> user = userRepository.findById(UUID.fromString(userId));
        // Map to response DTO if user exist.
        return user.map(value -> UserResponse.builder()
                .id(String.valueOf(value.getId()))
                .firstName(value.getFirstName())
                .lastName(value.getLastName())
                .email(value.getEmail())
                .phone(value.getPhone())
                .address(value.getAddress())
                .country(String.valueOf(value.getCountry()))
                .username(value.getUserCredentials().getUsername())
                .build());
    }

    @Override
    public Optional<UserCredentialsResponse> getUserCredentials(String userId) {
        Optional<UserCredentials> userCredentials = userCredentialsRepository.findById(UUID.fromString(userId));
        // Map to response DTO if user exist.
        return userCredentials.map(value -> UserCredentialsResponse.builder()
                .username(value.getUsername())
                .password(value.getPassword())
                .build());
    }

    @Override
    public UserResponse updateUser(String id, UserUpdateRequest request, String username) {
        User user = userRepository.findById(UUID.fromString(request.getId()))
                .orElseThrow(() -> new UserNotFoundException("User {} " + request.getId() + " not found."));

        // Update fields only if they are provided
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        if (request.getCountry() != null) user.setCountry(Country.valueOf(request.getCountry()));

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId().toString())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .address(savedUser.getAddress())
                .country(String.valueOf(savedUser.getCountry()))
                .build();
    }

    @Override
    public UserResponse updateUserCredentials(UserCredentialsUpdateRequest request) {
        User user = userRepository.findById(UUID.fromString(request.getId()))
                .orElseThrow(() -> new UserNotFoundException("User {} " + request.getId() + " not found."));

        UserCredentials userCredentials = user.getUserCredentials();
        if (userCredentials == null) {
            userCredentials = UserCredentials.builder()
                    .username(request.getUsername())
                    .password(request.getPassword()).build();
        }

        if (!Objects.equals(userCredentials.getUsername(), request.getUsername())) {
            userCredentials.setUsername(request.getUsername());
        }

        if (!Objects.equals(userCredentials.getPassword(), request.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userCredentials.setPassword(encoder.encode(request.getPassword()));
        }

        UserCredentials savedUserCredentials = userCredentialsRepository.save(userCredentials);
        user.setUserCredentials(savedUserCredentials);
        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .country(String.valueOf(user.getCountry()))
                .username(user.getUserCredentials().getUsername())
                .build();
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFoundException("User {} " + userId + " not found."));

        userRepository.delete(user);
        log.info("User {} deleted.", user);
    }

}
