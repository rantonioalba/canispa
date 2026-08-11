package com.devr.cani.spa.Auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devr.cani.spa.controller.base.BaseController;
import com.devr.cani.spa.dto.response.ApiResponse;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController extends BaseController {

    private final AuthService authService;


    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
        try {
            log.info("Login attempt for user: {}", request.getUsername());
            LoginResponse loginResponse = authService.login(request);
            return responseSuccess("Login successful", loginResponse);
        } catch (Exception e) {
            log.error("Login failed for user: {}. Error: {}", request.getUsername(), e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request){
        AuthResponse authResponse = authService.register(request);
        return responseSuccess("Registration successful", authResponse);
    }
}
