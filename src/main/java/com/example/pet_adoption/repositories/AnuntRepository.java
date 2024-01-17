package com.example.pet_adoption.repositories;

import com.example.pet_adoption.model.Anunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnuntRepository extends JpaRepository<Anunt, Long>{

    @Query("SELECT u FROM Anunt u WHERE u.name = :name")
    List<Anunt> findByName(String name);

    @Query("SELECT u FROM Anunt u WHERE u.user.id = :userId")
    List<Anunt> findByUserId(Long userId);
}
