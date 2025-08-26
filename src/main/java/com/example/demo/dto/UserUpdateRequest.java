package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class UserUpdateRequest {

    @NotNull(message = "User ID is required")
    private String id;  // UUID as string

    private String firstName;
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    private Integer phone;
    private String address;
    private String country;
}
