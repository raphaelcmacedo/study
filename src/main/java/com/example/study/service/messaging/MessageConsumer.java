package com.example.study.service.messaging;

import com.example.study.entity.Message;
import com.example.study.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConsumer {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ObjectMapper mapper;

    @KafkaListener(topics = Topic.MESSAGE, groupId = Topic.GROUP_ID)
    public void receiveMessage(@Payload Message message){
        log.info("Receiving message from queue: {} ", message);
        messageRepository.save(message);
    }
}
