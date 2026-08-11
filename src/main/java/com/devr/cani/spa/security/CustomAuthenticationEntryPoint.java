package com.devr.cani.spa.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        log.warn("Authentication required: {}", authException.getMessage());

        log.info("Request URI: {}", request.getRequestURI());

        log.info(request.getRequestURI().contains("/auth/login") ? "Request is for login endpoint" : "Request is NOT for login endpoint");

        // If path is api/auth/login, don't show authentication error
        if (request.getRequestURI().contains("/auth/login")) {
            // let that AuthenticationFailureHandler handle the response    
            log.info("Skipping AuthenticationEntryPoint for login endpoint");
            return;
        }

        ApiResponse<Void> apiResponse = ApiResponse.error(
            ResponseCode.UNAUTHORIZED.getCode(),
            "Authentication required. Please log in to access this resource."
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }


}
