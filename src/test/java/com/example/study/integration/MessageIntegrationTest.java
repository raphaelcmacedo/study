package com.example.study.integration;

import com.example.study.entity.Message;
import com.example.study.factory.MessageFactory;
import com.example.study.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MessageRepository messageRepository;

    private Long id = 1L;

    @BeforeEach
    void setup(){
        messageRepository.deleteAll();

        Message message = MessageFactory.buildAnyMessage();
        messageRepository.save(message);
        id = message.getId();
    }

    @Test
    void findByIdReturnsMessageWhenValidId() {
        ResponseEntity<org.openapitools.model.Message> response = restTemplate.exchange("/message/" + id, HttpMethod.GET, null, org.openapitools.model.Message.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void createValidMessage(){
        MessageRequest messageRequest = MessageFactory.buildAnyRequest();
        HttpEntity<MessageRequest>  httpEntity = new HttpEntity<>(messageRequest);

        ResponseEntity<org.openapitools.model.Message> response = restTemplate.exchange("/message", HttpMethod.POST, httpEntity, org.openapitools.model.Message.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
