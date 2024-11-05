package ru.itmo.is_lab1.util;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.Properties;

public class JwtUtil {
    // Секретный ключ для подписи токенов
    private String SECRET_KEY;
    private long EXPIRATION_TIME;

    public JwtUtil() {
        try {
            Properties properties = new Properties();
            properties.load(JwtUtil.class.getResourceAsStream("/jwt.properties"));
            SECRET_KEY = properties.getProperty(PROPS.SECRET_KEY);
            EXPIRATION_TIME = Long.parseLong(properties.getProperty(PROPS.EXPIRATION_TIME));
        } catch (Exception e){
            System.err.println("Can not load resource!");
        }
    }

    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Добавляем subject (имя пользователя)
                .setIssuedAt(new Date()) // Устанавливаем дату выпуска
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Устанавливаем время жизни токена
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Подписываем токен с помощью секретного ключа и HS256
                .compact();
    }

    public String verifyToken(String token) throws JwtException {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Устанавливаем секретный ключ для проверки подписи
                .build()
                .parseClaimsJws(token); // Парсим и проверяем токен

        return claims.getBody().getSubject(); // Возвращаем subject (имя пользователя)
    }

    private interface PROPS{
        String SECRET_KEY = "secret_key";
        String EXPIRATION_TIME = "expiration_time";
    }
}
