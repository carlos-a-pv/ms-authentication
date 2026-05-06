package com.example.jwt_security.service.impl;

import com.example.jwt_security.constant.ApplicationConstant;
import com.example.jwt_security.dto.request.JwtRequestDTO;
import com.example.jwt_security.dto.request.UserRequestDTO;
import com.example.jwt_security.dto.response.JwtResponseDTO;
import com.example.jwt_security.dto.response.UserResponseDTO;
import com.example.jwt_security.entity.User;
import com.example.jwt_security.exception.AlreadyExistException;
import com.example.jwt_security.exception.ResourceNotFoundException;
import com.example.jwt_security.mapper.UserMapper;
import com.example.jwt_security.repository.UserRepository;
import com.example.jwt_security.security.JwtService;
import com.example.jwt_security.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public JwtResponseDTO login(JwtRequestDTO request) {
        log.info("login method got called with username : {} and password : {}",request.getUsername(), request.getPassword());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(ApplicationConstant.USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException(ApplicationConstant.INVALID_CREDENTIALS);

        String token = jwtService.generateToken(user.getUsername());
        JwtResponseDTO responseDTO = new JwtResponseDTO();
        responseDTO.setToken(token);
        responseDTO.setUsername(user.getUsername());
        return responseDTO;
    }

    @Override
    public UserResponseDTO register(UserRequestDTO request) {
        log.info("register method got called with username : {} and password : {}",request.getUsername(), request.getPassword());
        boolean exists = userRepository.existsByUsername(request.getUsername());
        if(exists)
            throw new AlreadyExistException(ApplicationConstant.USER_ALREADY_EXIST);
        User user = userMapper.toEntity(request);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserResponseDTO resetPassword(UserRequestDTO request) {
        return null;
    }

    public void createDefaultUser(String email){

    }
}
