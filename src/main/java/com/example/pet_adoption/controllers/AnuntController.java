package com.example.pet_adoption.controllers;

import com.example.pet_adoption.exceptions.NoAnuntFoundByIdException;
import com.example.pet_adoption.model.Anunt;
import com.example.pet_adoption.model.User;
import com.example.pet_adoption.services.AnuntService;
import com.example.pet_adoption.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/anunt")
public class AnuntController {
    @Autowired
    private AnuntService anuntService;
    @Value("${images.upload.directory}")
    private String uploadDirectory;
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping
    public List<Anunt> getAllAnunturi(){
        return anuntService.getAllAnunturi();
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/anunt/{name}")
    public List<Anunt> getAnuntByName (@PathVariable String name) {
        return anuntService.getAnuntByName(name);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteAnuntById (@PathVariable final long id) {
        boolean deleted = anuntService.deleteAnuntById(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        // Construiește calea completă a imaginii
        String imagePath = uploadDirectory + "/" + imageName;

        // Încarcă imaginea și returnează-o sub formă de răspuns
        Resource imageResource = new FileSystemResource(imagePath);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // sau MediaType.IMAGE_PNG, în funcție de tipul imaginilor
                .body(imageResource);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/like/{id}")
    public ResponseEntity<Anunt> toggleLike(@PathVariable Long id) {
        try {
            Anunt updatedAnunt = anuntService.toggleLike(id);
            return new ResponseEntity<>(updatedAnunt, HttpStatus.OK);
        } catch (NoAnuntFoundByIdException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/userId/{userId}")
    public List<Anunt> getAnuntByUserId (@PathVariable Long userId) {
        return anuntService.getAnunturiByUserId(userId);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Anunt> editAnunt(@PathVariable Long id, @RequestBody Anunt updatedAnunt) {
        try {
            Anunt existingAnunt = anuntService.getAnuntById(id);
            if (existingAnunt != null) {
                System.out.println("Solicitarea a ajuns în editAnunt cu id: " + id);
                // Actualizează detaliile anunțului cu noile informații primite în corpul cererii
                existingAnunt.setName(updatedAnunt.getName());
                existingAnunt.setDescription(updatedAnunt.getDescription());
                // Alte câmpuri pentru actualizare

                // Salvează anunțul actualizat în baza de date
                Anunt savedAnunt = anuntService.saveAnunt(existingAnunt);

                //HttpHeaders headers = new HttpHeaders();
                //headers.add("Access-Control-Allow-Origin", "http://127.0.0.1:5500");

                return ResponseEntity.ok()
                        .body(savedAnunt);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoAnuntFoundByIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/old/edit/{id}")
    public ResponseEntity<Anunt> getAnuntForEdit(@PathVariable Long id) {
        try {
            Anunt existingAnunt = anuntService.getAnuntById(id);
            if (existingAnunt != null) {
                return ResponseEntity.ok(existingAnunt);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoAnuntFoundByIdException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping
    public ResponseEntity<Anunt> createAnunt(
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestPart("image") MultipartFile image,
            @RequestParam("nrLikes") int nrLikes,
            @RequestParam("likedByCurrentUser") boolean likedByCurrentUser) {

        try {
            Anunt anunt = new Anunt();
            anunt.setName(title);
            anunt.setDescription(description);

            // Salvează imaginea și actualizează calea în obiectul Anunt
            String imagePath = saveImage(image);
            anunt.setPath(imagePath);
            User user = userService.getUserById(userId);
            anunt.setNrLikes(nrLikes);
            anunt.setLikedByCurrentUser(likedByCurrentUser);
            anunt.setUser(user);

            // Salvează obiectul Anunt în baza de date
            Anunt savedAnunt = anuntService.saveAnunt(anunt);

            return ResponseEntity.ok()
                    .header("Access-Control-Allow-Origin", "http://127.0.0.1:5500")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(savedAnunt);
        } catch (IOException e) {
            // Tratează eroarea în mod corespunzător
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            // Dacă există o altă problemă, tratează-o aici
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        // Generează un nume unic pentru imagine
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String imageName = dateFormat.format(new Date()) + "_" + image.getOriginalFilename();

        // Construiește calea completă a imaginii
        String imagePath = uploadDirectory + "/" + imageName;

        // Salvează imaginea pe disc
        Path destination = Paths.get(imagePath);
        Files.copy(image.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return imagePath;
    }

}
