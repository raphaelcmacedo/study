package com.example.study.service;

import com.example.study.entity.Message;
import com.example.study.exception.MessageNotFoundException;
import com.example.study.repository.MessageRepository;
import com.example.study.service.messaging.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageProducer messageProducer;

    public Message findById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(String.format("Message with id %s not found", id)));
    }

    public void save(Message message){
        messageProducer.sendMessage(message);
    }
}
