package com.danilodps.kafkaconsumer.application.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message);
    }
}
