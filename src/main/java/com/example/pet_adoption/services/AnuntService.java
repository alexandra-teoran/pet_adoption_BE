package com.example.pet_adoption.services;

import com.example.pet_adoption.repositories.AnuntRepository;
import com.example.pet_adoption.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnuntService {
    @Autowired
    private AnuntRepository anuntRepository;
}
