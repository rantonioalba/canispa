package com.devr.cani.spa.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    SUCCESS("000", "Success"),
    CREATED("001", "Registration successful"),
    UPDATED("002", "Update successful"),
    DELETED("003", "Deletion successful"),
    UNAUTHORIZED("401", "Token expired"),
    FORBIDDEN("403", "Forbidden"),
    CONFLICT("409", "Conflict"),
    TOKEN_EXPIRED("401-01", "Invalid token"),
    INVALID_TOKEN("401-02", "Invalid token"),

    // Error codes
    BAD_REQUEST("400", "Bad request"),
    NOT_FOUND("404", "Not found"),
    VALIDATION_ERROR("422", "Validation error"),    
    DUPLICATE_ENTRY("409", "Duplicate entry"),

    // Server error codes
    INTERNAL_ERROR("500", "Internal server error");


    private final String code;
    private final String message;

}
