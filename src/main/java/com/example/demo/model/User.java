package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    Integer id;



    @Column(nullable = false)
    @NotBlank
    @Pattern(
            regexp = "^[\\w.%+-^\"]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
            message = "L'email doit être valide et contenir une extension d'au moins 2 caractères."
    )
    String email;

    @Column(nullable = false)

    String password;

    boolean admin;

    @ManyToOne
    @JsonIgnore
    Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_skill",
            inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    List<Skills> skills;

}
