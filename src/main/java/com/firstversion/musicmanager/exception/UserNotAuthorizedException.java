package com.firstversion.musicmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotAuthorizedException extends RuntimeException{
    public UserNotAuthorizedException(String message) {
        super(message);
    }
}
