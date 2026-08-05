package com.devr.cani.spa.exception;
import java.util.Date;

// Custom exception class for token expiration scenarios
public class TokenExpiredException extends RuntimeException {
    private String token;
    private Date expirationDate;


    public TokenExpiredException(String message) {
        super(message);
    }

    public TokenExpiredException(String token, Date expirationDate) {
        super(String.format("JWT token expired at: %s", expirationDate));
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
