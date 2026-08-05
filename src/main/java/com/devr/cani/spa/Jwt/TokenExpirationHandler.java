package com.devr.cani.spa.Jwt;

import org.springframework.stereotype.Component;

import com.devr.cani.spa.dto.response.ApiResponse;
import com.devr.cani.spa.enums.ResponseCode;
import com.devr.cani.spa.exception.TokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenExpirationHandler {

    private final ObjectMapper objectMapper;

    public void handleTokenExpired(HttpServletResponse response, TokenExpiredException ex) throws IOException, JsonProcessingException, java.io.IOException {
        ApiResponse<Void> apiResponse = ApiResponse.error(
            ResponseCode.TOKEN_EXPIRED.getCode(),
            "Your session has expired. Please log in again.");
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        
    }

    public void handleInvalidToken(HttpServletResponse response, Exception ex) throws IOException, JsonProcessingException, java.io.IOException {
        ApiResponse<Void> apiResponse = ApiResponse.error(
            ResponseCode.INVALID_TOKEN.getCode(),
            "Invalid or malformed token: " + ex.getMessage()
        );
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }   

    public void handleGenericTokenError(HttpServletResponse response, Exception ex) throws IOException, JsonProcessingException, java.io.IOException {
        ApiResponse<Void> apiResponse = ApiResponse.error(
            ResponseCode.INTERNAL_ERROR.getCode(),
            "There was an error with the token. Please log in again. " + ex.getMessage()
        );
        
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
