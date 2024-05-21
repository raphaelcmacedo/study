package com.example.study.controller;

import com.example.study.service.MessageService;
import com.example.study.util.MapperUtil;
import org.openapitools.api.MessageApi;
import org.openapitools.model.Message;
import org.openapitools.model.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController implements MessageApi {

    @Autowired
    private MessageService messageService;
    private final MapperUtil mapperUtil = new MapperUtil();

    @Override
    public ResponseEntity<Message> findById(Integer id) {
        com.example.study.entity.Message message = messageService.findById(Long.valueOf(id));
        Message messageDto = mapperUtil.map(message, Message.class);
        return ResponseEntity.ok(messageDto);
    }

    @Override
    public ResponseEntity<Void> save(MessageRequest messageRequest) {
        com.example.study.entity.Message message = mapperUtil.map(messageRequest, com.example.study.entity.Message.class);
        messageService.save(message);
        return ResponseEntity.noContent().build();
    }
}
