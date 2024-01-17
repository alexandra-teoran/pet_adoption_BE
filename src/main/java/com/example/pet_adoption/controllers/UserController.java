package com.example.pet_adoption.controllers;

import com.example.pet_adoption.model.User;
import com.example.pet_adoption.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.*;

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
    public User getUserById (@PathVariable  final long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User loginUser) {
        // Verifică credențialele utilizatorului
        User existingUser = userService.getUserByEmail(loginUser.getEmail());
        System.out.println("login");
        if (existingUser != null && existingUser.getPassword().equals(loginUser.getPassword())) {
            // Autentificare reușită
            System.out.println("in if");
            String token = generateToken(existingUser.getId());

            // Returnează un obiect JSON care conține token și userId
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", existingUser.getId());

            System.out.println("Conectare reușită! User ID: " + existingUser.getId());
            return ResponseEntity.ok(response);
        } else {
            // Autentificare eșuată
            System.out.println("in else");
            System.out.println("Eșec la conectare. User: " + loginUser.getEmail() + ", Password: " + loginUser.getPassword());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Email sau parolă incorecte."));        }
    }

    private String generateToken(long userId) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // Expiră în 10 zile
                .signWith(secretKey)
                .compact();
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
