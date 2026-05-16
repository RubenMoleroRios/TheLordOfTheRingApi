package com.ruben.lotr.core.auth.infrastructure.security;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ruben.lotr.core.auth.application.service.JwtTokenGenerator;
import com.ruben.lotr.core.auth.domain.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenGeneratorImpl implements JwtTokenGenerator {

    private final SecretKey secretKey;
    private final long expirationMillis;

    public JwtTokenGeneratorImpl(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMillis) {

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMillis = expirationMillis;
    }

    @Override
    public String generate(User user) {

        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(user.id().value())
                .claim("email", user.email().value())
                .claim("name", user.name().value())
                .claim("role", user.role().name())
                .claim("permissions", user.role().permissions().stream().map(permission -> permission.name()).toList())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserId(String token) {
        return getClaims(token).getSubject();
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public List<String> getPermissions(String token) {
        Claims claims = getClaims(token);
        Object permissions = claims.get("permissions");
        if (permissions instanceof List<?> rawPermissions) {
            return rawPermissions.stream().map(String::valueOf).toList();
        }
        return List.of();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
