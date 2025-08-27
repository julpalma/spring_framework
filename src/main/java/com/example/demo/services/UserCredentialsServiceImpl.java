package com.example.demo.services;

import com.example.demo.dto.*;
import com.example.demo.model.UserCredentials;
import com.example.demo.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Service Layer (Business logic)
//Spring security block all endpoints unless configured.
@Service
@Slf4j
@RequiredArgsConstructor //handles dependency injection
public class UserCredentialsServiceImpl implements UserCredentialsServiceInterface {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentials userCredentials = userCredentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Map your User to Spring Security's UserDetails
        return org.springframework.security.core.userdetails.User.builder()
                .username(userCredentials.getUsername())
                .password(userCredentials.getPassword()) // hashed password
                .roles("USER") // assign roles, adjust as needed
                .build();
    }
}

