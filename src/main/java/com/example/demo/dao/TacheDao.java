package com.example.demo.dao;

import com.example.demo.model.Tache;
import com.example.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.List;

@Repository
public interface TacheDao extends JpaRepository<Tache, Integer> {

    @Query("FROM " +
            "   Tache t" +
            "  JOIN" +
            "   t.priorite p  " +
            "  JOIN " +
            "   t.utilisateur u  " +
            "ORDER BY " +
            "   p.id ASC")
    List<Tache> orderTacheNative();

    List<Tache> findByNom(String nom);

    public List<Tache> findByUtilisateurId(Integer id);


}
