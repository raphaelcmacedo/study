package com.example.study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name= "sender", nullable = false)
    private String from;
    @Column(name= "recipient", nullable = false)
    private String to;
    @Column(nullable = false)
    private String content;

}
