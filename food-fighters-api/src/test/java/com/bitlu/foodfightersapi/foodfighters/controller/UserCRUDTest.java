package com.bitlu.foodfightersapi.foodfighters.controller;
import com.bitlu.foodfightersapi.foodfighters.controller.UserController;
import com.bitlu.foodfightersapi.foodfighters.model.User;
import com.bitlu.foodfightersapi.foodfighters.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class UserCRUDTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("user1", "user1@example.com", "password1"));
        userList.add(new User("user2", "user2@example.com", "password2"));

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userController.getAllUsers();

        assertEquals(userList.size(), result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        User user = new User("user1", "user1@example.com", "password1");
        user.setUserId("1");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        ResponseEntity<User> responseEntity = userController.getUserById("1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void testCreateUser() {
        User user = new User("user1", "user1@example.com", "password1");

        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User("user1", "user1@example.com", "password1");
        existingUser.setUserId("1");

        User updatedUser = new User("user1_updated", "user1_updated@example.com", "password1_updated");

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        ResponseEntity<User> responseEntity = userController.updateUser("1", updatedUser);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedUser, responseEntity.getBody());
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void testDeleteUser() {
        ResponseEntity<User> responseEntity = userController.deleteUser("1");

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userRepository, times(1)).deleteById("1");
    }
}
