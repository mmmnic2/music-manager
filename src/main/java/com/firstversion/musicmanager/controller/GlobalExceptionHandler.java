package com.firstversion.musicmanager.controller;

import com.firstversion.musicmanager.dto.response.ResponseObject;
import com.firstversion.musicmanager.exception.AlreadyExistException;
import com.firstversion.musicmanager.exception.NotFoundException;
import com.firstversion.musicmanager.exception.UserNotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseObject> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null)
        );
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ResponseObject> handleAlreadyExistException(AlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject(HttpStatus.CONFLICT.value(), ex.getMessage(), null)
        );
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ResponseObject> handle(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ResponseObject(HttpStatus.FORBIDDEN.value(), ex.getMessage(), null)
        );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseObject> handleIllegalArgumentException(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null)
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null)
        );
    }

}
