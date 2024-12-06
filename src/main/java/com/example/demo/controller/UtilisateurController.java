package com.example.demo.controller;

import com.example.demo.dao.UtilisateurDao;
import com.example.demo.dao.TacheDao;
import com.example.demo.dao.PrioriteDao;
import com.example.demo.model.Droit;
import com.example.demo.model.Priorite;
import com.example.demo.model.Tache;
import com.example.demo.model.Utilisateur;
import com.example.demo.security.IsAdmin;
import com.example.demo.security.IsEmploye;
import com.example.demo.view.UtilisateurTacheView;
import com.example.demo.view.UtilisateurView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private TacheDao tacheDao;

    @Autowired
    private PrioriteDao prioriteDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @IsAdmin
    @GetMapping("/Utilisateur")
    @JsonView(UtilisateurView.class)
    public List<Utilisateur> getAll() {
        return utilisateurDao.findAll();
    }

    @IsAdmin
    @GetMapping("/Utilisateur/{id}")
    @JsonView(UtilisateurTacheView.class)
    public ResponseEntity<Utilisateur> get(@PathVariable Integer id) {

        // Vérifier si l'utilisateur existe dans la base de données
        Optional<Utilisateur> ListUtilisateur = utilisateurDao.findById(id);

        if (ListUtilisateur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Utilisateur utilisateur = ListUtilisateur.get();

        // Charger la liste des tâches associées à cet utilisateur
        List<Tache> tache = tacheDao.findByUtilisateurId(id);  // Assurez-vous que vous avez une méthode dans le TacheDao

        utilisateur.setTache(tache);  // Ajouter la liste de tâches à l'utilisateur

        return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    }

    @IsAdmin
    @PostMapping("/UtilisateurCreate")
    public ResponseEntity<Utilisateur> create(@RequestBody @Valid Utilisateur utilisateur) {
        // Force l'id à null pour éviter un bug côté client
        utilisateur.setId(null);

        // Encoder le mot de passe de l'utilisateur
        utilisateur.setPassword(encoder.encode(utilisateur.getPassword()));

        // Créer et associer le droit "employe"
        Droit employeDroit = new Droit();
        employeDroit.setId(1);  // L'ID pour "employe" dans votre table Droit
        utilisateur.setDroit(employeDroit);

        // Sauvegarder l'utilisateur dans la base de données
        utilisateurDao.save(utilisateur);

        // Créer une tâche et lui associer une priorité
        Tache tache = new Tache();
        tache.setNom("Nouvelle Tâche");
        tache.setUtilisateur(utilisateur);  // Associer la tâche à l'utilisateur
        tache.setValide(false);  // Exemple : tâche non validée

        // Créer une priorité pour la tâche
        Priorite priorite = prioriteDao.findById(1).orElse(null);  // Associer la priorité (id = 1 pour "haute")
        if (priorite != null) {
            tache.setPriorite(priorite);
        }

        // Sauvegarder la tâche dans la base de données
        tacheDao.save(tache);

        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }

    @IsAdmin
    @PutMapping("/Utilisateur/{id}")
    public ResponseEntity<Utilisateur> update(@RequestBody @Valid Utilisateur utilisateurSend, @PathVariable Integer id) {

        // Forcer l'id de l'utilisateur pour éviter qu'il soit modifiable par le client
        utilisateurSend.setId(id);

        // Vérifier si l'utilisateur existe dans la base de données
        Optional<Utilisateur> ListUtilisateur = utilisateurDao.findById(id);

        if (ListUtilisateur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Utilisateur utilisateurDB = ListUtilisateur.get();

        // Si le mot de passe n'est pas nul, on le garde de côté et on encode le nouveau mot de passe
        if (utilisateurSend.getPassword() != null && !utilisateurSend.getPassword().equals(utilisateurDB.getPassword())) {
            utilisateurSend.setPassword(encoder.encode(utilisateurSend.getPassword()));
        } else {
            utilisateurSend.setPassword(utilisateurDB.getPassword());  // Garder l'ancien mot de passe
        }

        if (utilisateurDB.getTache() != null) {
            utilisateurSend.setTache(utilisateurDB.getTache());
        }

        if (utilisateurDB.getDroit() != null) {
            utilisateurSend.setDroit(utilisateurDB.getDroit());
        }

        utilisateurDao.save(utilisateurSend);

        return new ResponseEntity<>(utilisateurSend, HttpStatus.OK);
    }

    @IsAdmin
    @DeleteMapping("/Utilisateur/{id}")
    public ResponseEntity<Utilisateur> delete(@PathVariable Integer id) {

        Optional<Utilisateur> ListUtilisateur = utilisateurDao.findById(id);

        if (ListUtilisateur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        utilisateurDao.deleteById(id);

        return new ResponseEntity<>(ListUtilisateur.get(), HttpStatus.OK);
    }
}
