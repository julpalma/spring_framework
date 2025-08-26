package com.example.demo.model;

import com.example.demo.enums.Country;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "user")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private Integer phone;
    private String address;

    @Enumerated
    private Country country;
}
