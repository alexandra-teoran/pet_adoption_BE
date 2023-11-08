package com.example.pet_adoption.controllers;

import com.example.pet_adoption.services.AnuntService;
import com.example.pet_adoption.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/anunt")
public class AnuntController {
    @Autowired
    private AnuntService anuntService;
}
