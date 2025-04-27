package com.melvard.user_service.security;

import io.github.melihvardar.security.jwt.BaseJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final BaseJwtService jwtService;

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(userDetails.getUsername());
    }

    public String generateToken(Authentication authentication, UUID userId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(userDetails.getUsername(), userId);
    }

    public String generateToken(Authentication authentication, UUID userId, List<String> roles) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(userDetails.getUsername(), userId, roles);
    }

    public String generateTokenWithRoles(Authentication authentication, UUID userId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return jwtService.generateToken(userDetails.getUsername(), userId, roles);
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public String extractEmail(String token) {return jwtService.extractEmail(token);}

    public UUID extractUserId(String token) {
        return jwtService.extractUserId(token);
    }

    public List<String> extractRoles(String token) {
        return jwtService.extractRoles(token);
    }

}