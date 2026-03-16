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
