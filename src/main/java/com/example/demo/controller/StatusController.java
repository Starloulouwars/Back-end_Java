package com.example.demo.controller;

import com.example.demo.dao.StatusDao;
import com.example.demo.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class StatusController {

    @Autowired
    private StatusDao statusDao;

    @GetMapping("/Status")
    public List<Status> getAll(){

        return statusDao.findAll();

    }

    @GetMapping("/Status/{id}")
    public ResponseEntity<Status> get(@PathVariable Integer id){

        //on vérifie que l'status existe dans la bdd
        Optional<Status> optionalStatus = statusDao.findById(id);

        if (optionalStatus.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalStatus.get(), HttpStatus.OK);
    }

    @PostMapping("/Status")
    public ResponseEntity<Status> create(@RequestBody Status status) {
        //force l'id à null pour que le client ne fasse pas de bug
        status.setId(null);
        statusDao.save(status);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PutMapping("/Status/{id}")
    public ResponseEntity<Status> update(@RequestBody Status status, @PathVariable Integer id){

        //force l'id de l'status pour ne pas qu'elle soit modifiable
        status.setId(id);

        //on vérifie que l'status existe dans la bdd
        Optional<Status> optionalStatus = statusDao.findById(id);

        if (optionalStatus.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        statusDao.save(status);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @DeleteMapping("/Status/{id}")
    public ResponseEntity<Status> delete(@PathVariable Integer id){

        //on vérifie que l'status existe dans la bdd
        Optional<Status> optionalStatus = statusDao.findById(id);

        if (optionalStatus.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        statusDao.deleteById((id));

        return new ResponseEntity<>(optionalStatus.get(), HttpStatus.OK);
    }


}
