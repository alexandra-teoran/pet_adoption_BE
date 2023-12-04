package com.example.pet_adoption.controllers;

import com.example.pet_adoption.model.Anunt;
import com.example.pet_adoption.services.AnuntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/anunt")
public class AnuntController {
    @Autowired
    private AnuntService anuntService;

    @GetMapping
    public List<Anunt> getAllAnunturi(){
        return anuntService.getAllAnunturi();
    }

    @GetMapping("/anunt/{name}")
    public List<Anunt> getAnuntByName (@RequestParam final String name) {
        return anuntService.getAnuntByName(name);
    }
}
