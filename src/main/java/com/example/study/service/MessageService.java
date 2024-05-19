package com.example.study.service;

import com.example.study.entity.Message;
import com.example.study.exception.MessageNotFoundException;
import com.example.study.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    public Message findById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(String.format("Message with id %s not found", id)));
    }

    public Message save(Message message){
        return messageRepository.save(message);
    }
}
