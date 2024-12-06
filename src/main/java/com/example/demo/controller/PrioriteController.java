package com.example.demo.controller;

import com.example.demo.dao.PrioriteDao;
import com.example.demo.model.Priorite;
import com.example.demo.security.IsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class PrioriteController {

    @Autowired
    private PrioriteDao prioriteDao;

    // Endpoint pour récupérer toutes les priorités
    @IsAdmin
    @GetMapping("/Priorite")
    public List<Priorite> getAll() {
        return prioriteDao.findAll();
    }

    // Endpoint pour récupérer une priorité par son id
    @IsAdmin
    @GetMapping("/Priorite/{id}")
    public ResponseEntity<Priorite> get(@PathVariable Integer id) {

        // Vérifie que la priorité existe dans la base de données
        Optional<Priorite> ListPriorite = prioriteDao.findById(id);

        if (ListPriorite.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ListPriorite.get(), HttpStatus.OK);
    }

    // Endpoint pour créer une nouvelle priorité
    @IsAdmin
    @PostMapping("/Priorite")
    public ResponseEntity<Priorite> create(@RequestBody Priorite priorite) {
        // Force l'id à null pour éviter un bug côté client
        priorite.setId(null);
        prioriteDao.save(priorite);
        return new ResponseEntity<>(priorite, HttpStatus.CREATED);
    }

    // Endpoint pour mettre à jour une priorité existante
    @IsAdmin
    @PutMapping("/Priorite/{id}")
    public ResponseEntity<Priorite> update(@RequestBody Priorite priorite, @PathVariable Integer id) {

        // Force l'id de la priorité pour ne pas qu'elle soit modifiable par le client
        priorite.setId(id);

        // Vérifie que la priorité existe dans la base de données
        Optional<Priorite> ListPriorite = prioriteDao.findById(id);

        if (ListPriorite.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        prioriteDao.save(priorite);
        return new ResponseEntity<>(priorite, HttpStatus.OK);
    }

    // Endpoint pour supprimer une priorité
    @IsAdmin
    @DeleteMapping("/Priorite/{id}")
    public ResponseEntity<Priorite> delete(@PathVariable Integer id) {

        // Vérifie que la priorité existe dans la base de données
        Optional<Priorite> ListPriorite = prioriteDao.findById(id);

        if (ListPriorite.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        prioriteDao.deleteById(id);
        return new ResponseEntity<>(ListPriorite.get(), HttpStatus.OK);
    }
}
