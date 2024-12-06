package com.example.demo.model;

import com.example.demo.view.UtilisateurTacheView;
import com.example.demo.view.UtilisateurView;
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
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(UtilisateurTacheView.class)
    private String pseudo;
    private String code;
    private String password;

    // Autres attributs...

    @ManyToOne
    public Droit droit;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tache> tache;

    public void setCode(String code){
        this.code = code.toUpperCase();
    }
}

