package com.example.demo.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtUtils {

    public String generateToken(String email) {
        Map<String, Object> tokenData = new HashMap<>();
        return Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, "pizza")
                .compact();
    }

    public String exctractEmailFromJwt(String jwt){

        return Jwts.parser()
                .setSigningKey("pizza")
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

    }

}
