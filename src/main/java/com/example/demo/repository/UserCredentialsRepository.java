package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

//JPA Repository is an interface that simplifies data access in Java applications.

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, UUID> {
    Optional<UserCredentials> findByUserId(UUID userId);

    Optional<UserCredentials> findByUsername(String username);


}