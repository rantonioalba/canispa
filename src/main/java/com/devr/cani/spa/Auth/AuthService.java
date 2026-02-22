package com.devr.cani.spa.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devr.cani.spa.Jwt.JwtService;
import com.devr.cani.spa.User.Role;
import com.devr.cani.spa.User.User;
import com.devr.cani.spa.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        UserDetails user = userRepository.findByUsername(request.getUsername())
            .orElseThrow();
        String token = jwtService.getToken(user);
        return LoginResponse.builder()
            .token(token)
            .user((User) user)
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
       User user = User.builder()
            .username(request.getUsername())
            .name(request.getName())
            .paternalSurname(request.getPaternalSurname())
            .maternalSurname(request.getMaternalSurname())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
    }

}
