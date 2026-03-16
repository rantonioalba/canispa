package com.devr.cani.spa.controller.base;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devr.cani.spa.dto.response.ApiResponse;
import com.devr.cani.spa.enums.ResponseCode;

public abstract class BaseController {
    protected <T> ResponseEntity<ApiResponse<T>> responseSuccess(String message, T data) {
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }

    protected <T> ResponseEntity<ApiResponse<T>> responseSuccess(T data) {
        return ResponseEntity.ok(ApiResponse.success("Operation successful", data));
    }
    
    protected <T> ResponseEntity<ApiResponse<T>> responseSuccess(String message) {
        return ResponseEntity.ok(ApiResponse.success(message));
    }       

    protected <T> ResponseEntity<ApiResponse<T>> responseSuccess() {
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    protected <T> ResponseEntity<ApiResponse<T>> responseCreated(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<T>builder()
                        .code(ResponseCode.CREATED.getCode())
                        .success(true)
                        .message(ResponseCode.CREATED.getMessage())
                        .data(data)
                        .build());
    }
    
    protected <T> ResponseEntity<ApiResponse<T>> responseUpdated(T data) {
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .code(ResponseCode.UPDATED.getCode())
                        .success(true)
                        .message(ResponseCode.UPDATED.getMessage())
                        .data(data)
                        .build()
        );
    }

    protected <T> ResponseEntity<ApiResponse<T>> responseDeleted() {
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .code(ResponseCode.DELETED.getCode())
                        .success(true)
                        .message(ResponseCode.DELETED.getMessage())
                        .build()
        );
    }

    protected <T> ResponseEntity<ApiResponse<T>> responseError(String code, String message) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(code, message));
    }

    protected <T> ResponseEntity<ApiResponse<T>> responseError(String code, String message, T data) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(code, message, data));
    }

    protected <T> ResponseEntity<ApiResponse<T>> responseNotFound(String message) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ResponseCode.NOT_FOUND.getCode(), message));
    }

    protected <T> ResponseEntity<ApiResponse<T>> responseValidationError(String message) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponse.error(ResponseCode.VALIDATION_ERROR.getCode(), message));
    }   

    protected <T> ResponseEntity<ApiResponse<T>> responseDuplicateEntry(String message) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ResponseCode.DUPLICATE_ENTRY.getCode(), message));
    }   

    protected <T> ResponseEntity<ApiResponse<T>> responseInternalError(String message) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ResponseCode.INTERNAL_ERROR.getCode(), message));
    }

}
