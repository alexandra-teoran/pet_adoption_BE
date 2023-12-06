package com.example.pet_adoption.controllers;

import com.example.pet_adoption.model.User;
import com.example.pet_adoption.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUserById (@RequestParam final long id) {
        boolean deleted = userService.deleteUserById(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public User createUser(@RequestBody final User user) {
        return userService.saveUser(user);
    }
}
