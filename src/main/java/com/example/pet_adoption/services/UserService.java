package com.example.pet_adoption.services;

import com.example.pet_adoption.exceptions.NoUserFoundByIdException;
import com.example.pet_adoption.model.User;
import com.example.pet_adoption.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean deleteUserById (Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        else
            throw(new NoUserFoundByIdException(HttpStatus.NOT_FOUND));
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }
}
