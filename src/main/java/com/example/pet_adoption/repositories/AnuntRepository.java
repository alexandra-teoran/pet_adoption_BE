package com.example.pet_adoption.repositories;

import com.example.pet_adoption.model.Anunt;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AnuntRepository extends JpaRepository<Anunt, Long>{
}
