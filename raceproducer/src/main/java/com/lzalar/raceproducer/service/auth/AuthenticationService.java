package com.lzalar.raceproducer.service.auth;

import com.lzalar.raceproducer.domain.user.User;
import com.lzalar.raceproducer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public UUID getCurrentUserId(){
       JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
       return UUID.fromString(jwtAuthenticationToken.getName());
    }

    public User getCurrentUser(){
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findById(UUID.fromString(jwtAuthenticationToken.getName())).orElseThrow();
    }
}
