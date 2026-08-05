package com.devr.cani.spa.exception;

// Custom exception class for invalid token scenarios
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
