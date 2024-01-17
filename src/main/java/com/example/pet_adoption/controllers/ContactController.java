package com.example.pet_adoption.controllers;

import com.example.pet_adoption.model.Contact;
import com.example.pet_adoption.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping
    public Contact createContact(@RequestBody final Contact contact){
        return contactService.saveContact(contact);
    }
}
