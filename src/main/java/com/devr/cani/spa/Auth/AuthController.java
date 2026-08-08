package com.devr.cani.spa.Auth;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devr.cani.spa.controller.base.BaseController;
import com.devr.cani.spa.dto.response.ApiResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController extends BaseController {

    private final AuthService authService;


    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request){
        LoginResponse loginResponse = authService.login(request);
        return responseSuccess("Login successful", loginResponse);
    }

    @PostMapping(value = "register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request){
        AuthResponse authResponse = authService.register(request);
        return responseSuccess("Registration successful", authResponse);
    }
}
