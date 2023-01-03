package com.example.animalsshelter2.config;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.JWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import com.example.animalsshelter2.models.UserRole;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

@Component
public class JwtUtils {

    private final Key secret;

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secret = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserRole role, String email, Long id) throws IllegalArgumentException, JWTCreationException {
        return Jwts.builder()
                .setSubject("User Details")
                .claim("auth", role.getRole().toString().toLowerCase(Locale.ROOT))
                .claim("email", email)
                .claim("user_id", id)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(60, ChronoUnit.MINUTES)))
                .setIssuer("animals-shelter-2/project")
                .signWith(secret)
                .compact();
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        return Jwts.parserBuilder().setSigningKey(secret)
                .build().parseClaimsJws(token).getBody().get("email").toString();
    }

    public Boolean isTokenExpired(String token) {
        return JWT.decode(token).getExpiresAt().before(new Date());
    }

}




