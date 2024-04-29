package com.example.study.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping
    public String privatePage(@AuthenticationPrincipal OidcUser principal) {
        return "You are in the private section, so you are authenticated!";
    }

    @GetMapping("/user")
    public String getJwt(@AuthenticationPrincipal OidcUser principal){
        return String.format("""
                <h1>User Details<h1>
                <h3>Principal: %s </h3>
                <h3>Authority: %s </h3>
                <h3>JWT: %s </h3
                """,
                principal, principal.getAuthorities(), principal.getIdToken().getTokenValue());
    }
}
