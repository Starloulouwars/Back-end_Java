package com.example.demo.controller;

import com.example.demo.dto.PrioriteTacheDto;
import com.example.demo.model.Tache;
import com.example.demo.security.AppUtilisateurDetails;
import com.example.demo.security.IsEmploye;
import com.example.demo.security.IsRedacteur;
import com.example.demo.view.TacheView;
import com.example.demo.view.UtilisateurTacheView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class TacheController {

    @Autowired
    private com.example.demo.dao.TacheDao TacheDao;

    @IsEmploye
    @GetMapping("/Tache")
    @JsonView(UtilisateurTacheView.class)
    public List<Tache> getAll(){

        return TacheDao.orderTacheNative();

    }

    @IsEmploye
    @GetMapping("/Tache/{id}")
    @JsonView(TacheView.class)
    public ResponseEntity<Tache> get(@PathVariable Integer id){

        //on vérifie que l'Tache existe dans la bdd
        Optional<Tache> optionalTache = TacheDao.findById(id);

        if (optionalTache.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalTache.get(), HttpStatus.OK);
    }

    @IsRedacteur
    @PostMapping("/Tache/création")
    public ResponseEntity<Tache> create(@RequestBody Tache Tache) {
        Tache.setId(null);
        TacheDao.save(Tache);
        return new ResponseEntity<>(Tache, HttpStatus.CREATED);
    }

    @IsRedacteur
    @PutMapping("/PutTache/{id}")
    public ResponseEntity<Tache> update(@RequestBody Tache Tache, @PathVariable Integer id){

        //force l'id de l'Tache pour ne pas qu'elle soit modifiable
        Tache.setId(id);

        //on vérifie que l'Tache existe dans la bdd
        Optional<Tache> optionalTache = TacheDao.findById(id);

        if (optionalTache.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TacheDao.save(Tache);
        return new ResponseEntity<>(Tache, HttpStatus.OK);
    }

    @IsRedacteur
    @DeleteMapping("/DelTache/{id}")
    public ResponseEntity<Tache> delete(@PathVariable Integer id, @AuthenticationPrincipal AppUtilisateurDetails appUtilisateurDetails){

        //on vérifie que la Tache existe dans la bdd
        Optional<Tache> optionalTache = TacheDao.findById(id);

        if (optionalTache.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (appUtilisateurDetails.utilisateur.getId() == id || appUtilisateurDetails.utilisateur.droit.getId() == 3) {
            TacheDao.deleteById((id));
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(optionalTache.get(), HttpStatus.OK);
    }


}
