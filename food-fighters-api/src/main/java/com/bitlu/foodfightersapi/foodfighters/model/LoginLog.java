package com.bitlu.foodfightersapi.foodfighters.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "login_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog {
    @Id
    private String id;

    private String username;
    private Instant timestamp;
    private boolean success;
    private String message; // optional: "Invalid password", etc.
}