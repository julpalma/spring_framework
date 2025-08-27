package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class UserCredentialsResponse {
    private String username;
    private String password;
}

