package com.example.demo.controller;

import com.example.demo.dao.UserDao;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import com.example.demo.security.AppUserDetails;
import com.example.demo.security.IsAdmin;
import com.example.demo.security.IsUser;
import com.example.demo.view.UserSkillView;
import com.example.demo.view.UserView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    BCryptPasswordEncoder encoder;

    @IsUser
    @GetMapping("/User")
    @JsonView(UserView.class)
    public List<User> getAll(){

        return userDao.findAll();

    }

    @IsUser
    @GetMapping("/User/{id}")
    @JsonView(UserSkillView.class)
    public ResponseEntity<User> get(@PathVariable Integer id){

        //on vérifie que l'user existe dans la bdd
        Optional<User> optionalUser = userDao.findById(id);

        if (optionalUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @IsAdmin
    @PostMapping("/UserCreate")
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        //force l'id à null pour que le client ne fasse pas de bug
        user.setId(null);

        user.setPassword(encoder.encode(user.getPassword()));
        user.setAdmin(false);
        Status disponible = new Status();
        disponible.setId(1);
        user.setStatus(disponible);

        userDao.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @IsAdmin
    @PutMapping("/User/{id}")
    public ResponseEntity<User> update(@RequestBody @Valid User userSend, @PathVariable Integer id){

        //force l'id de l'user pour ne pas qu'elle soit modifiable
        userSend.setId(id);

        //on vérifie que l'user existe dans la bdd
        Optional<User> optionalUser = userDao.findById(id);

        if (optionalUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User userDB = optionalUser.get();

        if(userDB.getPassword() !=null){
            userSend.setPassword(encoder.encode(userDB.getPassword()));
        }

        if(userDB.getStatus() ==null){
            userSend.setStatus(userDB.getStatus());
        }

        if(userDB.getSkills() ==null){
            userSend.setSkills(userDB.getSkills());
        }

        if(userDB.getAdmin() == null){
            userSend.setAdmin(userDB.getAdmin());
        }

        userSend.setSkills(userDB.getSkills());

        userDao.save(userSend);

        return new ResponseEntity<>(userSend, HttpStatus.OK);
    }

    @IsAdmin
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
