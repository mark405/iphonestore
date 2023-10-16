package com.iphone.store.exception.handler;

import com.iphone.store.dto.ErrorResponseDto;
import com.iphone.store.exception.NoCustomerFoundException;
import com.iphone.store.exception.NoIphoneModelFoundException;
import com.iphone.store.exception.NoOrderWithSuchIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoCustomerFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoCustomerException(NoCustomerFoundException exception, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoIphoneModelFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoIphoneModelFoundException(NoIphoneModelFoundException exception, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoOrderWithSuchIdException.class)
    public ResponseEntity<ErrorResponseDto> handleNoOrderWithSuchIdException(NoOrderWithSuchIdException exception, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
