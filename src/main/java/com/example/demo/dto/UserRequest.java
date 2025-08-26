package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

//DTO (Data Transfer Object) for user creation.
//The API accepts JSON from the client, maps it to the DTO, and then uses the Lombok builder() to construct the entity.

@Getter
@Setter
@Builder
@Data
public class UserRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Phone number is required")
    private Integer phone;

    private String address;

    @NotBlank(message = "Country code is required")
    private String country;
}
