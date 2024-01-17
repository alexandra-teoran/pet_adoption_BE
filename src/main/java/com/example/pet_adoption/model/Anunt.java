package com.example.pet_adoption.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
    @Column(name="path", nullable = false)
    private String path;
    @Column(name="nrLikes", nullable = true)
    private int nrLikes;
    @Column(name = "likedByCurrentUser", nullable = true)
    private boolean likedByCurrentUser;
}
