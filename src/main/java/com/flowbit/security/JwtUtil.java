package com.flowbit.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 🔐 GENERATE TOKEN (email:role)
    public String generateToken(String data) {
        return Jwts.builder()
                .setSubject(data)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔍 EXTRACT EMAIL
    public String extractEmail(String token) {
        return getClaims(token).getSubject().split(":")[0];
    }

    // 🔍 EXTRACT ROLE
    public String extractRole(String token) {
        return getClaims(token).getSubject().split(":")[1];
    }

    // VALIDATE
    public boolean validateToken(String token, String email) {
        return email.equals(extractEmail(token)) && !isExpired(token);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}