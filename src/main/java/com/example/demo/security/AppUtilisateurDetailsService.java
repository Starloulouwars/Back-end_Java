package com.example.demo.security;

import com.example.demo.dao.UtilisateurDao;
import com.example.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUtilisateurDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        List<Utilisateur> listUtilisateur = utilisateurDao.findByPseudo(pseudo);

        if (listUtilisateur.isEmpty()) {
            throw new UsernameNotFoundException("Pseudo introuvable");
        }

        return new AppUtilisateurDetails(listUtilisateur.get(0)); // Accès au premier élément
    }
}
