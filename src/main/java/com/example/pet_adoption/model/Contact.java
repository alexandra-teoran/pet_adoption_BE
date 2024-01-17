package com.example.pet_adoption.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="contact", schema="public")
public class Contact extends BaseEntity{
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="subject", nullable = false)
    private String subject;
    @Column(name="message", nullable = false)
    private String message;
}
