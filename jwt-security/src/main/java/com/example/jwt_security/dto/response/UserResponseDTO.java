package com.example.jwt_security.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
     //added latest
}
