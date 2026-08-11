package com.devr.cani.spa.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devr.cani.spa.Jwt.JwtService;
import com.devr.cani.spa.User.Role;
import com.devr.cani.spa.User.User;
import com.devr.cani.spa.User.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        try {
            log.info("Attempting to authenticate user: {}", request.getUsername());
            // Authenticate the user using the AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );

            log.info("Authentication successful for user: {}", request.getUsername());

            // Retrieve the authenticated user's details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate a JWT token for the authenticated user
            String token = jwtService.getToken(userDetails);

            User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        // UserDetails user = userRepository.findByUsername(request.getUsername())
        //     .orElseThrow();
        // String token = jwtService.getToken(user);
        return LoginResponse.builder()
            .token(token)
            .user((User) user)
            .build();
        } catch (BadCredentialsException e) {
            log.warn("Invalid credentials for users: {}", request.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        } catch (Exception e) {
            log.error("Authentication failed for user: {}. Error: {}", request.getUsername(), e.getMessage());
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
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
