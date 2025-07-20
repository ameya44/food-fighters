package com.bitlu.foodfightersapi.foodfighters.repository;

import com.bitlu.foodfightersapi.foodfighters.model.LoginLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoginLogRepository extends MongoRepository<LoginLog, String> {
    List<LoginLog> findByUsername(String username);
}

