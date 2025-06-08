package com.bitlu.foodfightersapi.foodfighters.controller;

import com.bitlu.foodfightersapi.foodfighters.model.User;
import com.bitlu.foodfightersapi.foodfighters.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll(); // clean before each test
    }

    @Test
    void testRegisterUser() throws Exception {
        String json = """
            {
              "username": "testuser",
              "email": "test@example.com",
              "password": "password123"
            }
        """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User registered successfully")));
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Register first
        String registerJson = """
            {
              "username": "loginuser",
              "email": "login@example.com",
              "password": "secret123"
            }
        """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isOk());

        // Then login
        String loginJson = """
            {
              "username": "loginuser",
              "password": "secret123"
            }
        """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void testLoginFailure_WrongPassword() throws Exception {
        // Create user manually
        User user = new User();
        user.setUsername("wrongpass");
        user.setEmail("w@example.com");
        user.setPassword(new BCryptPasswordEncoder().encode("correct"));
        user.setRoles(List.of("USER"));
        userRepository.save(user);

        String json = """
            {
              "username": "wrongpass",
              "password": "incorrect"
            }
        """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());

    }
}
