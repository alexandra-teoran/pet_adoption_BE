package com.example.pet_adoption.controllers;

import com.example.pet_adoption.model.Anunt;
import com.example.pet_adoption.model.User;
import com.example.pet_adoption.services.AnuntService;
import com.example.pet_adoption.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/anunt")
public class AnuntController {
    @Autowired
    private AnuntService anuntService;
    @Value("${images.upload.directory}")
    private String uploadDirectory;
    @Autowired
    private UserService userService;
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
    public ResponseEntity<Anunt> createAnunt(
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestPart("image") MultipartFile image) {

        try {

            // Crează un obiect Anunt utilizând datele primite
            Anunt anunt = new Anunt();
            anunt.setName(title);
            anunt.setDescription(description);

            // Salvează imaginea și actualizează calea în obiectul Anunt
            String imagePath = saveImage(image);
            anunt.setPath(imagePath);
            User user = userService.getUserById(userId);
            anunt.setUser(user);
            System.out.println("Received userId: " + userId);
            System.out.println("Received title: " + title);
            System.out.println("Received description: " + description);
            // Salvează obiectul Anunt în baza de date
            Anunt savedAnunt = anuntService.saveAnunt(anunt);

            return new ResponseEntity<>(savedAnunt, HttpStatus.OK);
        }catch (IOException e) {
            // Tratează eroarea în mod corespunzător
            e.printStackTrace();
            return null; // sau aruncă o excepție corespunzătoare
        }
    }



    private String saveImage(MultipartFile image) throws IOException {
        // Generează un nume unic pentru imagine
        String imageName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        // Construiește calea completă a imaginii
        String imagePath = uploadDirectory + File.separator + imageName;

        // Salvează imaginea pe disc
        Path destination = Paths.get(imagePath);
        Files.copy(image.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return imagePath;
    }

}
