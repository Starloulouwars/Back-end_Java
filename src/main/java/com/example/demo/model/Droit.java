package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Droit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    // Relation One-to-Many avec Utilisateur (Un droit peut être attribué à plusieurs utilisateurs)
    @OneToMany(mappedBy = "droit")
    private List<Utilisateur> utilisateurs;
}
