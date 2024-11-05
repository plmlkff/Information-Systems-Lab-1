package ru.itmo.is_lab1.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import ru.itmo.is_lab1.security.service.JWTService;
import ru.itmo.is_lab1.security.service.impl.JWTServiceImpl;

import java.io.IOException;

@WebFilter(urlPatterns = "/api/*")
public class JWTFilter implements Filter {
    private final JWTService jwtService = new JWTServiceImpl();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    }
}
