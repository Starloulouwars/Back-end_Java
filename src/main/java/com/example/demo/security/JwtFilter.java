package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AppUtilisateurDetailsService appUtilisateurDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearer = request.getHeader("Authorization");

        if(bearer != null) {
            String jwt = bearer.substring(7);
            String pseudo = jwtUtils.exctractpseudoFromJwt(jwt);

            UserDetails UserDetails = appUtilisateurDetailsService.loadUserByUsername(pseudo);

            UsernamePasswordAuthenticationToken UsernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(UserDetails, null, UserDetails.getAuthorities());
            UsernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
