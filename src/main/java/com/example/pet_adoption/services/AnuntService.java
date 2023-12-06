package com.example.pet_adoption.services;

import com.example.pet_adoption.exceptions.NoAnuntFoundByIdException;
import com.example.pet_adoption.exceptions.NoAnuntFoundByNameException;
import com.example.pet_adoption.model.Anunt;
import com.example.pet_adoption.repositories.AnuntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnuntService {
    @Autowired
    private AnuntRepository anuntRepository;

    public List<Anunt> getAllAnunturi(){
        return anuntRepository.findAll();
    }

    public List<Anunt> getAnuntByName (final String name) {
        Optional<List<Anunt>> anunturi = Optional.ofNullable(anuntRepository.findByName(name));
        return anunturi.orElseThrow(() -> new NoAnuntFoundByNameException(HttpStatus.NOT_FOUND));
    }

    public boolean deleteAnuntById (Long id) {
        if (anuntRepository.existsById(id)) {
            anuntRepository.deleteById(id);
            return true;
        }
        else
            throw(new NoAnuntFoundByIdException(HttpStatus.NOT_FOUND));
    }

    public Anunt saveAnunt(final Anunt anunt) {
        return anuntRepository.save(anunt);
    }
}
