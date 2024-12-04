package com.example.demo.dao;

import com.example.demo.dto.StatSkillDto;
import com.example.demo.model.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface SkillDao extends JpaRepository<Skills, Integer> {

    @Query("SELECT new com.example.demo.dto.StatSkillDto(s.name, COUNT(u)) FROM Skills s LEFT JOIN s.user u GROUP BY s.name")
    List<StatSkillDto> numberUserWithSkill();

    @Query("SELECT new com.example.demo.dto.StatSkillDto(s.name, COUNT(*)) FROM Skills s LEFT JOIN user_skill us ON s.id = us.skills_id GROUP BY s.name")
    List<Objects[]> numberUserWithSkillNative();

}
