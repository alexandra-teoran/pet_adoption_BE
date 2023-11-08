package com.example.pet_adoption.repositories;


import com.example.pet_adoption.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
