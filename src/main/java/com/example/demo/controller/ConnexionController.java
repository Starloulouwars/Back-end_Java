package com.example.demo.controller;

import com.example.demo.dao.UtilisateurDao;
import com.example.demo.model.Droit;
import com.example.demo.model.Priorite;
import com.example.demo.model.Tache;
import com.example.demo.model.Utilisateur;
import com.example.demo.security.AppUtilisateurDetails;
import com.example.demo.security.IsAdmin;
import com.example.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class ConnexionController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    UtilisateurDao utilisateurDao;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @IsAdmin
    @PostMapping("/new-account")
    public ResponseEntity<Map<String, Object>> inscription(@RequestBody Utilisateur utilisateur) {

        utilisateur.setPassword(encoder.encode(utilisateur.getPassword()));

        Droit employeDroit = new Droit();
        employeDroit.setId(1);
        utilisateur.setDroit(employeDroit);

        utilisateurDao.save(utilisateur);

        return ResponseEntity.ok(Map.of("message", "Utilisateur enregistré avec succès"));
    }



    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody Utilisateur utilisateur) {
        try {
            AppUtilisateurDetails appUtilisateurDetails = (AppUtilisateurDetails) authenticationProvider
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    utilisateur.getPseudo(),
                                    utilisateur.getPassword()))
                    .getPrincipal();
            return ResponseEntity.ok(jwtUtils.generateToken(appUtilisateurDetails.getUsername()));

        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/test-jwt")
    public String testjwt() {
        return jwtUtils.generateToken("a@a.com");
    }
}
