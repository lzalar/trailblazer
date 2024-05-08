package com.lzalar.raceconsumer.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationService {

    public UUID getCurrentUserId(){
        if(SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken jwtAuthenticationToken){
            return UUID.fromString(jwtAuthenticationToken.getName());
        }
        throw new IllegalStateException();
    }
}
