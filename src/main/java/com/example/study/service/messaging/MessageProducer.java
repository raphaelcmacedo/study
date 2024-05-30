package com.example.study.service.messaging;

import com.example.study.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageProducer {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ObjectMapper mapper;

    public void sendMessage(Message message) {
        log.info("Sending message to queue: {} ", message);
        kafkaTemplate.send(Topic.MESSAGE, message);
    }
}
