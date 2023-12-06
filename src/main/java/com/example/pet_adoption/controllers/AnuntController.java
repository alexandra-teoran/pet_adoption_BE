package com.example.pet_adoption.controllers;

import com.example.pet_adoption.model.Anunt;
import com.example.pet_adoption.services.AnuntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteAnuntById (@RequestParam final long id) {
        boolean deleted = anuntService.deleteAnuntById(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Anunt createAnunt(@RequestBody final Anunt anunt) {
        return anuntService.saveAnunt(anunt);
    }
}
