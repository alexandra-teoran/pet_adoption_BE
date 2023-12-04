package com.example.pet_adoption.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="users", schema="public")
public class User extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "password", nullable = false)
    private String password;

}
