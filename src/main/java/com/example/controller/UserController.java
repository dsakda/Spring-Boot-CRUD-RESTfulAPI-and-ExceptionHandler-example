package com.example.controller;

import com.example.entity.User;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
        User exitingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        exitingUser.setFirstName(user.getFirstName());
        exitingUser.setLastName(user.getLastName());
        exitingUser.setEmail(user.getEmail());
        return userRepository.save(exitingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long userId) {
        User exitingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        userRepository.delete(exitingUser);
        return ResponseEntity.ok().build();
    }
}
