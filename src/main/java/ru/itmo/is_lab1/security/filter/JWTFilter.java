package ru.itmo.is_lab1.security.filter;

import jakarta.annotation.Priority;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itmo.is_lab1.security.service.JWTService;
import ru.itmo.is_lab1.security.service.impl.JWTServiceImpl;

import java.io.IOException;

@WebFilter(urlPatterns = "/api/*")
@Priority(2)
public class JWTFilter implements Filter {
    public static final String LOGIN_ATTRIBUTE_NAME = "token_login";
    private final int ACCESS_DENIED_ERROR = 403;

    private final JWTService jwtService = new JWTServiceImpl();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getMethod().equals("OPTIONS")){
            response.setStatus(200);
            return;
        }
        String path = request.getRequestURI();
        if (AllowedPath.contains(path)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token = getTokenFromRequest(request);
        if (!jwtService.verify(token)) {
            response.sendError(ACCESS_DENIED_ERROR);
            return;
        }
        request.setAttribute(LOGIN_ATTRIBUTE_NAME, jwtService.getUsername(token));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return "";
    }

    private enum AllowedPath{
        LOGIN("/api/user/auth"),
        SIGNUP("/api/user/signup"),
        TEST("/api/hello-world"),
        WEBSOCKET("/api/domain/changed");
        private final String uri;
        AllowedPath(String uri){
            this.uri = uri;
        }

        public static boolean contains(String uri){
            for (AllowedPath path : AllowedPath.values()){
                if (uri.contains(path.uri)) return true;
            }
            return false;
        }
    }
}
