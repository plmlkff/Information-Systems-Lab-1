package ru.itmo.is_lab1.security.service;

import io.jsonwebtoken.JwtException;

public interface JWTService {
    String create(String username);

    String getUsername(String token) throws JwtException;

    boolean verify(String token);
}
