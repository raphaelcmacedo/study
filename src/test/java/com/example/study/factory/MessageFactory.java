package com.example.study.factory;

import com.example.study.entity.Message;
import org.openapitools.model.MessageRequest;

public class MessageFactory {

    public static Message buildAnyMessage() {
        return Message.builder()
                .from("Me")
                .to("You")
                .content("Content")
                .build();
    }

    public static org.openapitools.model.Message buildAnyDTO() {
        return new org.openapitools.model.Message()
                .from("Me")
                .to("You")
                .content("Content");
    }

    public static MessageRequest buildAnyRequest(){
        return new MessageRequest()
                .from("Me")
                .to("You")
                .content("Content");
    }
}
