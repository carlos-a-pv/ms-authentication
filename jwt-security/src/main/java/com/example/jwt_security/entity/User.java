package com.example.jwt_security.entity;

import com.example.jwt_security.entity.enums.Role;
import com.example.jwt_security.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
    private Status status;

}
