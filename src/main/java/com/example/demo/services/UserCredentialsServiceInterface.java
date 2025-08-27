package com.example.demo.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserCredentialsServiceInterface {

    UserDetails loadUserByUsername(String username);

}
