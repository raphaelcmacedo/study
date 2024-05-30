package com.example.study.service;

import com.example.study.entity.Message;
import com.example.study.exception.MessageNotFoundException;
import com.example.study.factory.MessageFactory;
import com.example.study.repository.MessageRepository;
import com.example.study.service.messaging.MessageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    MessageService messageService;
    @Mock
    MessageRepository messageRepository;
    @Mock
    private MessageProducer messageProducer;
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdReturnsMessage(){
        Message expected = MessageFactory.buildAnyMessage();
        Long id = 1L;

        given(messageRepository.findById(anyLong())).willReturn(Optional.ofNullable(expected));

        Message result = messageService.findById(id);

        assertEquals(expected, result);
        then(messageRepository).should().findById(anyLong());
    }

    @Test
    void findByIdThrowsWhenNotExist(){
        Long id = 1L;

        given(messageRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(MessageNotFoundException.class, () -> messageService.findById(id));
    }

    @Test
    void createReturnsMessage(){
        Message message = MessageFactory.buildAnyMessage();
        willDoNothing().given(messageProducer).sendMessage(any(Message.class));

        messageService.save(message);

        then(messageProducer).should().sendMessage(any(Message.class));
    }

}
