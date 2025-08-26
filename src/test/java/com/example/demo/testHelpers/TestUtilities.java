package com.example.demo.testHelpers;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.enums.Country;
import com.example.demo.model.User;

import java.util.UUID;

public final class TestUtilities {

    public static User user = buildUser();

    public static User buildUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Juliana")
                .lastName("Palma")
                .email("juliana@example.com")
                .phone(123456789)
                .address("123 Main St")
                .country(Country.Canada)
                .build();
    }

    public static UserRequest buildUserRequest() {
        return UserRequest.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .country(user.getCountry().toString())
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

}
