package com.example.study.controller;

import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;
import com.example.study.configuration.SecurityConfig;
import com.example.study.entity.Message;
import com.example.study.exception.MessageNotFoundException;
import com.example.study.factory.MessageFactory;
import com.example.study.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.openapitools.model.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MessageController.class, excludeAutoConfiguration = OAuth2AuthorizedClientManager.class)
@AutoConfigureAddonsWebmvcResourceServerSecurity
@Import(SecurityConfig.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private MessageService messageService;

    @Test
    void findByIdReturnsMessage() throws Exception {
        Message message = MessageFactory.buildAnyMessage();
        Long id = 1L;

        given(messageService.findById(anyLong())).willReturn(message);

        mockMvc.perform(get("/message/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdThrows404WhenIdIsNotFound() throws Exception {
        Long id = 1L;

        given(messageService.findById(anyLong())).willThrow(new MessageNotFoundException(""));

        mockMvc.perform(get("/message/{id}", id))
               .andExpect(status().isNotFound());
    }

    @Test
    void saveReturns204WithValidRequest() throws Exception {
        MessageRequest request = MessageFactory.buildAnyRequest();
        String json = mapper.writeValueAsString(request);

        willDoNothing().given(messageService).save(any(Message.class));

        mockMvc.perform(post("/message").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void saveThrows400WhenRequestIsInvalid() throws Exception {
        MessageRequest request = new MessageRequest();
        String json = mapper.writeValueAsString(request);

        willDoNothing().given(messageService).save(any(Message.class));

        mockMvc.perform(post("/message").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
