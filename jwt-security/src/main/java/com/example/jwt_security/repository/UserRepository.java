package com.example.jwt_security.repository;

import com.example.jwt_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndUsername(String email, String username);
    boolean existsByEmail(String email);
}
