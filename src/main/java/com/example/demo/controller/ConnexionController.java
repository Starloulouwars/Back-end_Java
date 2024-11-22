package com.example.demo.controller;


import com.example.demo.dao.StatusDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import com.example.demo.security.AppUserDetails;
import com.example.demo.security.JwtUtils;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class ConnexionController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @PostMapping("/new-account")
    public ResponseEntity<Map<String, Object>> inscription(@RequestBody User user){

        user.setPassword(encoder.encode(user.getPassword()));
        user.setAdmin(false);
        Status disponible = new Status();
        disponible.setId(1);
        userDao.save(user);
        user.setStatus(disponible);

        return ResponseEntity.ok(Map.of("message", "Register done"));

    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody User user) {

        try {
            AppUserDetails appUserDetails = (AppUserDetails) authenticationProvider
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    user.getPassword()))
                    .getPrincipal();

            return ResponseEntity.ok(jwtUtils.generateToken(appUserDetails.getUsername()));

        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/test-jwt")
    public String testJwt() {
        return jwtUtils.generateToken("a@a.com");
    }

}
