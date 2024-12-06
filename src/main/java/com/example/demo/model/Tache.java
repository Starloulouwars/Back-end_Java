package com.example.demo.model;

import com.example.demo.view.TacheView;
import com.example.demo.view.UtilisateurTacheView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(TacheView.class)
    private String nom;

    @JsonView(TacheView.class)
    private String description;

    @JsonView(TacheView.class)
    private boolean valide;

    @ManyToOne
    @JsonView(UtilisateurTacheView.class)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;  // Un utilisateur peut avoir plusieurs tâches

    @ManyToOne
    @JsonView(UtilisateurTacheView.class)
    @JoinColumn(name = "priorite_id")
    private Priorite priorite;  // Une tâche a une priorité
}
