package com.example.study.controller;

import com.example.study.PostNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handlePostNotFoundException(PostNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>("Not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
