package com.example.demo.api;

import com.example.demo.dto.*;
import com.example.demo.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

//API Layer to handle login
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/user")
public class UserAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
                                         
    @PostMapping("/login")
        public ResponseEntity<?> login(@Valid @RequestBody UserCredentialsUpdateRequest request) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = tokenProvider.generateToken(authentication);

                return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
            } catch (AuthenticationException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\":\"Invalid credentials\"}");
            }
        }
}
