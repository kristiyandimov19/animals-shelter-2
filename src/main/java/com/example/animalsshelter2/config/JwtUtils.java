/*package com.example.animalsshelter2.config;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import com.example.animalsshelter2.models.UserRole;
import com.example.animalsshelter2.models.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Locale;

@Component
public class JwtUtils {
    @Value("secret")
    private String secret;

    public String generateToken(UserRole role, String email, Long id) throws IllegalArgumentException, JWTCreationException {

        return JWT.create()
                .withSubject("User Details")
                .withClaim("auth", role.getRole().toString().toLowerCase(Locale.ROOT))
                .withClaim("email", email)
                .withClaim("user_id", id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .withIssuer("animals-shelter-2/project")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("animals-shelter-2/project")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }

}

*/

package com.example.animalsshelter2.config;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import io.jsonwebtoken.Claims;
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
import java.util.function.Function;

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
        String claim = Jwts.parserBuilder().setSigningKey(secret)
                .build().parseClaimsJws(token).getBody().get("email").toString();

        return claim;
    }

    public Boolean isTokenExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

}




