package ru.itmo.is_lab1.security.service.impl;

import io.jsonwebtoken.JwtException;
import jakarta.enterprise.context.ApplicationScoped;
import ru.itmo.is_lab1.security.service.JWTService;
import ru.itmo.is_lab1.util.JwtUtil;

@ApplicationScoped
public class JWTServiceImpl implements JWTService {
    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    public String create(String username) {
        return jwtUtil.createToken(username);
    }

    @Override
    public String getUsername(String token) throws JwtException {
        return jwtUtil.verifyToken(token);
    }

    @Override
    public boolean verify(String token) {
        try{
            jwtUtil.verifyToken(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
