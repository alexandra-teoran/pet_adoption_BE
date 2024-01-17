package com.example.pet_adoption.repositories;

import com.example.pet_adoption.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
