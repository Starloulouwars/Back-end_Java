package com.example.demo.controller;

import com.example.demo.dao.SkillDao;
import com.example.demo.dto.StatSkillDto;
import com.example.demo.model.Skills;
import com.example.demo.view.SkillView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class SkillsController {

    @Autowired
    private SkillDao SkillsDao;

    @GetMapping("/Skills")
    @JsonView(SkillView.class)
    public List<Skills> getAll(){

        return SkillsDao.findAll();

    }

    @GetMapping("/Skills/{id}")
    @JsonView(SkillView.class)
    public ResponseEntity<Skills> get(@PathVariable Integer id){

        //on vérifie que l'Skills existe dans la bdd
        Optional<Skills> optionalSkills = SkillsDao.findById(id);

        if (optionalSkills.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalSkills.get(), HttpStatus.OK);
    }

    @PostMapping("/Skills")
    public ResponseEntity<Skills> create(@RequestBody Skills Skills) {
        //force l'id à null pour que le client ne fasse pas de bug
        Skills.setId(null);
        SkillsDao.save(Skills);
        return new ResponseEntity<>(Skills, HttpStatus.CREATED);
    }

    @PutMapping("/Skills/{id}")
    public ResponseEntity<Skills> update(@RequestBody Skills Skills, @PathVariable Integer id){

        //force l'id de l'Skills pour ne pas qu'elle soit modifiable
        Skills.setId(id);

        //on vérifie que l'Skills existe dans la bdd
        Optional<Skills> optionalSkills = SkillsDao.findById(id);

        if (optionalSkills.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SkillsDao.save(Skills);
        return new ResponseEntity<>(Skills, HttpStatus.OK);
    }

    @DeleteMapping("/Skills/{id}")
    public ResponseEntity<Skills> delete(@PathVariable Integer id){

        //on vérifie que l'Skills existe dans la bdd
        Optional<Skills> optionalSkills = SkillsDao.findById(id);

        if (optionalSkills.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SkillsDao.deleteById((id));

        return new ResponseEntity<>(optionalSkills.get(), HttpStatus.OK);
    }

    @GetMapping("/stat-skill")
    public List<StatSkillDto> getStatSkill() {

        //ArrayList<Map<String, Object>> result = new ArrayList<>();

        //List<Skills> listSkill = SkillsDao.findAll();

        //for(Skills skills : listSkill){

            //Map<String, Object> skillJson = new HashMap();
            //skillJson.put("name", skills.getName());
            //skillJson.put("numberUser", skills.getUser().size());
            //result.add(skillJson);
        //}

        return SkillsDao.numberUserWithSkill();

    }


}
