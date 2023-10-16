package com.iphone.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoOrderWithSuchIdException extends RuntimeException{
    public NoOrderWithSuchIdException(String message) {
        super(message);
    }
}
