package com.example.demo.model;

import com.example.demo.view.UserSkillView;
import com.example.demo.view.UserView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(UserView.class)
    Integer id;



    @Column(nullable = false)
    @NotBlank
    @Pattern(
            regexp = "^[\\w.%+-^\"]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
            message = "L'email doit être valide et contenir une extension d'au moins 2 caractères."
    )
    @JsonView(UserView.class)
    String email;

    @Column(nullable = false)

    String password;

    Boolean admin;

    @ManyToOne
    @JsonIgnore
    Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_skill",
            inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    @JsonView(UserSkillView.class)
    List<Skills> skills;


}
