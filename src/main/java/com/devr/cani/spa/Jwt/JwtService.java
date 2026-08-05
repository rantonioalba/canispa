package com.devr.cani.spa.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.devr.cani.spa.Config.JwtConfig;
import com.devr.cani.spa.exception.InvalidTokenException;
import com.devr.cani.spa.exception.MalformedTokenException;
import com.devr.cani.spa.exception.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    
    @Autowired
    private JwtConfig jwtConfig;


    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact(); 

    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        try {
            return getClaimFromToken(token, Claims::getSubject);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(token, e.getClaims().getExpiration());
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: " + e.getMessage());
        }        
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (TokenExpiredException e) {
            throw e; // Rethrow the TokenExpiredException to be handled by the caller
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: " + e.getMessage());
        }
        
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(token, e.getClaims().getExpiration());
        } catch (UnsupportedJwtException e) {
            throw new MalformedTokenException("Unsupported JWT token: " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new MalformedTokenException("Malformed JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("JWT token compact of handler are invalid: " + e.getMessage());
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: " + e.getMessage());
        }        
    }

    public <T> T getClaimFromToken(String token, java.util.function.Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(token, e.getClaims().getExpiration());
        } catch (UnsupportedJwtException e) {
            throw new MalformedTokenException("Unsupported JWT token: " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new MalformedTokenException("Malformed JWT token: " + e.getMessage());
        } catch (SecurityException e) {
            throw new InvalidTokenException("Invalid JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("JWT token compact of handler are invalid: " + e.getMessage());
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: " + e.getMessage());
        }
    }

    private Date getExpirationDateFromToken(String token) {
        try {
            return getClaimFromToken(token, Claims::getExpiration);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(token, e.getClaims().getExpiration());
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: " + e.getMessage());
        }       
    }

    private boolean isTokenExpired(String token) {
        try {
            Date expirationDate = getExpirationDateFromToken(token);
            boolean isExpired = expirationDate.before(new Date());
            if (isExpired) {
                throw new TokenExpiredException(token, expirationDate);
            }
            return false; // Token is not expired
        } catch (TokenExpiredException e) {
            throw e; // Rethrow the TokenExpiredException to be handled by the caller
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token: " + e.getMessage());
        }        
    }

}
