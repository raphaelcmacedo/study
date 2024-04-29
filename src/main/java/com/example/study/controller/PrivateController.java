package com.example.study.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping("/")
    public String privatePage() {
        return "You are in the private section, so you are authenticated!";
    }

    public String getJwt(@AuthenticationPrincipal Jwt jwt){
        return String.format("""
                JWT: %s
                Principal: %s
                """, jwt.getTokenValue(), jwt.getClaims());
    }
}
