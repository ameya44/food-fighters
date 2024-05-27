package com.bitlu.foodfightersapi.foodfighters.controller;

import com.bitlu.foodfightersapi.foodfighters.FoodFightersApplication;
import com.bitlu.foodfightersapi.foodfighters.model.User;
import com.bitlu.foodfightersapi.foodfighters.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
      return  userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        logger.info("Finding User by ID {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Saving User  {}", user);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
    Optional<User> userEdit = userRepository.findById(id);
        if (userEdit.isPresent()) {
            user.setUserId(id);
            user.setUpdatedAt(new Date());
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable String id) {
        logger.info("Deleting User  {}", id);
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}