package com.example.demo.security;

import com.example.demo.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUtilisateurDetails implements UserDetails {

    public Utilisateur utilisateur;

    public AppUtilisateurDetails(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Comparer l'ID du droit avec les entiers correspondants
        if (utilisateur.getDroit() != null) {
            if (utilisateur.getDroit().getId() == 3) { // admin
                return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else if (utilisateur.getDroit().getId() == 2) { // redacteur
                return List.of(new SimpleGrantedAuthority("ROLE_REDACTEUR"));
            }
        }
        // employe
        return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYE"));
    }

    @Override
    public String getPassword() {
        return utilisateur.getPassword();
    }

    @Override
    public String getUsername() {
        return utilisateur.getPseudo();
    }
}