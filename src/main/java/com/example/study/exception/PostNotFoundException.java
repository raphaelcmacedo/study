package com.example.study.exception;

public class PostNotFoundException extends RuntimeException {
public PostNotFoundException(String message) {
        super(message);
    }
}
