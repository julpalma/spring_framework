package com.example.demo.testHelpers;

import com.example.demo.dto.UserCredentialsUpdateRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.enums.Country;
import com.example.demo.model.User;
import com.example.demo.model.UserCredentials;

import java.util.UUID;

public final class TestUtilities {

    public static User user = buildUser();

    public static User buildUser() {
        UserCredentials userCredentials = buildUserCredentials();
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Juliana")
                .lastName("Palma")
                .email("juliana@example.com")
                .phone(123456789)
                .address("123 Main St")
                .country(Country.Canada)
                .userCredentials(userCredentials)
                .build();
    }

    public static UserCredentials buildUserCredentials() {
        return UserCredentials.builder()
                .id(UUID.randomUUID())
                .username("juliana@example.com")
                .password("123").build();
    }

    public static UserRequest buildUserRequest() {
        return UserRequest.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .country(user.getCountry().toString())
                .username(user.getUserCredentials().getUsername())
                .password(user.getUserCredentials().getPassword())
                .build();
    }

    public static UserResponse buildUserResponse() {
        return UserResponse.builder()
                .id(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .country(user.getCountry().toString())
                .username(user.getUserCredentials().getUsername())
                .build();
    }

    //Update new address
    public static UserUpdateRequest buildUserUpdateRequest() {
        return UserUpdateRequest.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .address("456 New Street")
                .build();
    }

    public static UserResponse buildUserResponseUpdated() {
        return UserResponse.builder()
                .id(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(buildUserUpdateRequest().getAddress())
                .country(user.getCountry().toString())
                .build();
    }

    public static UserCredentialsUpdateRequest buildUserCredentialsUpdateRequest() {
        return UserCredentialsUpdateRequest.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .username(user.getUserCredentials().getUsername())
                .password(user.getUserCredentials().getPassword())
                .build();
    }

}
