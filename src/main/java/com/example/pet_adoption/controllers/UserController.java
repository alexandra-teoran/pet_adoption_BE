package com.example.pet_adoption.controllers;

import com.example.pet_adoption.model.User;
import com.example.pet_adoption.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUserById (@RequestParam final long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginUser) {
        // Verifică credențialele utilizatorului
        User existingUser = userService.getUserByEmail(loginUser.getEmail());

        if (existingUser != null && existingUser.getPassword().equals(loginUser.getPassword())) {
            // Autentificare reușită
            return ResponseEntity.ok("Conectare reușită!");
        } else {
            // Autentificare eșuată
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email sau parolă incorecte.");
        }
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
