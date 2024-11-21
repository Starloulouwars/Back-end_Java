package com.example.demo.controller;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/User")
    public List<User> getAll(){

        return userDao.findAll();

    }

    @GetMapping("/User/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id){

        //on vérifie que l'user existe dans la bdd
        Optional<User> optionalUser = userDao.findById(id);

        if (optionalUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @PostMapping("/User")
    public ResponseEntity<User> create(@RequestBody User user) {
        //force l'id à null pour que le client ne fasse pas de bug
        user.setId(null);
        userDao.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/User/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Integer id){

        //force l'id de l'user pour ne pas qu'elle soit modifiable
        user.setId(id);

        //on vérifie que l'user existe dans la bdd
        Optional<User> optionalUser = userDao.findById(id);

        if (optionalUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDao.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/User/{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id){

        //on vérifie que l'user existe dans la bdd
        Optional<User> optionalUser = userDao.findById(id);

        if (optionalUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDao.deleteById((id));

        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }


}
