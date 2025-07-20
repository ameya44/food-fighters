package com.bitlu.foodfightersapi.foodfighters.service;

import com.bitlu.foodfightersapi.foodfighters.config.JwtUtil;
import com.bitlu.foodfightersapi.foodfighters.dto.AuthRequest;
import com.bitlu.foodfightersapi.foodfighters.dto.AuthResponse;
import com.bitlu.foodfightersapi.foodfighters.dto.RegisterRequest;
import com.bitlu.foodfightersapi.foodfighters.model.LoginLog;
import com.bitlu.foodfightersapi.foodfighters.model.User;
import com.bitlu.foodfightersapi.foodfighters.repository.LoginLogRepository;
import com.bitlu.foodfightersapi.foodfighters.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of("USER"));

        userRepository.save(user);
        return "User registered successfully";
    }

    @Autowired
    private LoginLogRepository loginLogRepository;

    public AuthResponse login(AuthRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        boolean success = false;
        String message;

        if (userOpt.isEmpty()) {
            message = "User not found";
        } else {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                success = true;
                message = "Login successful";
            } else {
                message = "Invalid password";
            }
        }

        // Save login log
        loginLogRepository.save(new LoginLog(
                null,
                request.getUsername(),
                Instant.now(),
                success,
                message
        ));

        if (!success) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
        }

        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }
}

