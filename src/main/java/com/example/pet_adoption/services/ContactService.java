package com.example.pet_adoption.services;

import com.example.pet_adoption.model.Contact;
import com.example.pet_adoption.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact saveContact(final Contact contact){
        return contactRepository.save(contact);
    }
}
