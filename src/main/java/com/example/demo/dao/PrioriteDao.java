package com.example.demo.dao;

import com.example.demo.model.Priorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioriteDao extends JpaRepository<Priorite, Integer> {
}
