package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class UserCredentialsUpdateRequest {

    @NotNull(message = "User ID is required")
    private String id;  // UUID as string

    private String username;
    private String password;
}
