package com.example.study.exception;

public class SubjectAlreadyExistsException extends RuntimeException {

    public SubjectAlreadyExistsException(String message) {
        super(message);
    }
}

