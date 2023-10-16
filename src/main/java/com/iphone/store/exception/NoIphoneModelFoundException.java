package com.iphone.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoIphoneModelFoundException extends RuntimeException{
    public NoIphoneModelFoundException(String message) {
        super(message);
    }
}