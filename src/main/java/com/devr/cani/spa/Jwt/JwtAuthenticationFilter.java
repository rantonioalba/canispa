package com.devr.cani.spa.Jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.devr.cani.spa.exception.InvalidTokenException;
import com.devr.cani.spa.exception.MalformedTokenException;
import com.devr.cani.spa.exception.TokenExpiredException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailService;
    private final TokenExpirationHandler tokenExpirationHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String token = getTokenFromRequest(request);
        final String username;

        try {
            if(token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                username = jwtService.getUsernameFromToken(token);
            } catch (TokenExpiredException e) {
                // Handle the exception, e.g., log it or send an error response
                tokenExpirationHandler.handleTokenExpired(response, e);
                return; // Stop further processing
            } catch (InvalidTokenException  | MalformedTokenException e) {
                // Handle other exceptions related to token parsing
                tokenExpirationHandler.handleInvalidToken(response, e);
                return; // Stop further processing
            }
            
        


            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                try {
                    if(jwtService.isTokenValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (TokenExpiredException e) {
                    // Handle the exception, e.g., log it or send an error response
                    tokenExpirationHandler.handleTokenExpired(response, e);
                    return; // Stop further processing
                } catch (InvalidTokenException  | MalformedTokenException e) {
                    // Handle other exceptions related to token parsing
                    tokenExpirationHandler.handleInvalidToken(response, e);
                    return; // Stop further processing
                }
                
                try {
                    if(jwtService.isTokenValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (TokenExpiredException e) {
                    // Handle the exception, e.g., log it or send an error response
                    tokenExpirationHandler.handleTokenExpired(response, e);
                    return; // Stop further processing
                } catch (InvalidTokenException  | MalformedTokenException e) {
                    // Handle other exceptions related to token parsing
                    tokenExpirationHandler.handleInvalidToken(response, e);
                    return; // Stop further processing
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // Handle the exception, e.g., log it or send an error response
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
       final String bearerToken = request.getHeader("Authorization");
       if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
           return bearerToken.substring(7);
       }
       return null;
    }

}
