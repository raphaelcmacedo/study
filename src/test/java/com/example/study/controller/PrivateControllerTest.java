package com.example.study.controller;

import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;
import com.example.study.configuration.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = PrivateController.class, excludeAutoConfiguration = OAuth2AuthorizedClientManager.class)
@AutoConfigureAddonsWebmvcResourceServerSecurity
@Import(SecurityConfig.class)
public class PrivateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void privateReturns401IfNotAuthenticated() throws Exception {
        mockMvc.perform(get("/private"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void privateReturns200IfAuthenticated() throws Exception {
        String expected = "You are in the private section, so you are authenticated!";

        mockMvc.perform(get("/private"))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains(expected));
    }

    @Test
    //@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void privateJwtReturnsJwtInfoIfAuthenticated() throws Exception {
        String expected = "User Details";

        mockMvc.perform(get("/private/user").with(oidcLogin()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> result.getResponse().getContentAsString().contains(expected));
    }




}
