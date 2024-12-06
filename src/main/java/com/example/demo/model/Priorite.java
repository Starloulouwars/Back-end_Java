package com.example.demo.model;

import com.example.demo.view.TacheView;
import com.example.demo.view.UtilisateurTacheView;
import com.fasterxml.jackson.annotation.JsonView;
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
public class Priorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(TacheView.class)
    private String nom;

    // Relation One-to-Many avec Tache (Une priorite peut être partagée par plusieurs tache)
    @OneToMany(mappedBy = "priorite")
    private List<Tache> tache;
}
