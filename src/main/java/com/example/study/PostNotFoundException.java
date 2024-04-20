package com.example.study;

public class PostNotFoundException extends RuntimeException {
public PostNotFoundException(String message) {
        super(message);
    }
}
