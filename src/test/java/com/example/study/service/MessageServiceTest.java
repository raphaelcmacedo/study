package com.example.study.service;

import com.example.study.entity.Message;
import com.example.study.exception.MessageNotFoundException;
import com.example.study.factory.MessageFactory;
import com.example.study.repository.MessageRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    MessageService messageService;
    @Mock
    MessageRepository messageRepository;
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
        Message expected = MessageFactory.buildAnyMessage();

        given(messageRepository.save(any(Message.class))).willReturn(expected);

        Message result = messageService.save(expected);

        assertEquals(expected, result);
        then(messageRepository).should().save(any(Message.class));
    }

}
