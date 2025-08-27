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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private Integer phone;
    private String address;

    @Enumerated
    private Country country;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserCredentials userCredentials;
}
