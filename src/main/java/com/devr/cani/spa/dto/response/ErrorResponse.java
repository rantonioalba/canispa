package com.devr.cani.spa.dto.response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> details;

    public ErrorResponse(String code, String message, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
        this.details = new HashMap<>();
    }

   public ErrorResponse(String code, String message, LocalDateTime timestamp, Map<String, String> details) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
    }
}
