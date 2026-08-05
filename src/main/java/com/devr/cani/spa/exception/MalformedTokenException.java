package com.devr.cani.spa.exception;

// Custom exception class for malformed token scenarios
public class MalformedTokenException extends RuntimeException {

    public MalformedTokenException(String message) {
        super(message);
    }
}
