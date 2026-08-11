package com.devr.cani.spa.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.devr.cani.spa.dto.response.ApiResponse;
import com.devr.cani.spa.enums.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j  
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        log.warn("Login failed: {}", exception.getMessage());
        
        // Set the response status and content type
        String errorMessage;
        String errorCode;

        if (exception.getMessage().contains("Bad credentials")) {
            errorMessage = "Invalid email or password. Please try again.";
            errorCode = ResponseCode.UNAUTHORIZED.getCode();
        } else if (exception.getMessage().contains("User not found")) {
            errorMessage = "User not found with the provided email.";
            errorCode = ResponseCode.NOT_FOUND.getCode();
        } else if (exception.getMessage().contains("User is disabled")) {
            errorMessage = "User account is disabled";
            errorCode = ResponseCode.FORBIDDEN.getCode();
        } else if (exception.getMessage().contains("User account is locked")) {
            errorMessage = "User account has been locked";
            errorCode = ResponseCode.FORBIDDEN.getCode();
        } else {
            errorMessage = "Authentication failed. Please try again.";
            errorCode = ResponseCode.UNAUTHORIZED.getCode();
        }

        ApiResponse<Void> apiResponse = ApiResponse.error(errorCode, errorMessage);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
