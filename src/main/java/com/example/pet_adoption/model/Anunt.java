package com.example.pet_adoption.model;

import jakarta.persistence.*;

@Entity
@Table(name="anunt", schema="public")
public class Anunt extends BaseEntity{
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="description", nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = true)
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
