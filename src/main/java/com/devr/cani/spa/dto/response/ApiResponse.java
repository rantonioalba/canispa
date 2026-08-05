package com.devr.cani.spa.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String code;
    private boolean success;
    private String message;
    private LocalDateTime timestamp;
    private T data;

    //Success response with data
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code("000")
                .success(true)
                .message(message)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    //Success response without data
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .code("000")
                .success(true)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();   
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .code("000")
                .success(true)
                .message("Operation successful")
                .build();   
    }

    //Error response
    public static <T> ApiResponse<T> error(String code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();   
    }

    //Error response with data
    public static <T> ApiResponse<T> error(String code, String message, T data) {
        return ApiResponse.<T>builder()
                .code(code)
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();   
    }
}
