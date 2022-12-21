package com.example.animalsshelter2.config;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import com.example.animalsshelter2.models.UserRole;
import com.example.animalsshelter2.models.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("secret")
    private String secret;

    //Tazi funkciq sum pipal
    public String generateToken(UserRole role, String email, Long id) throws IllegalArgumentException, JWTCreationException {

        if(role.getRole() == UserRoleEnum.ADMIN){
            return JWT.create()
                    .withSubject("User Details")
                    .withClaim("auth", "admin")
                    .withClaim("email", email)
                    .withClaim("user_id",id)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .withIssuer("animals-shelter-2/project")
                    .sign(Algorithm.HMAC256(secret));
        }
        else{
            return JWT.create()
                    .withSubject("User Details")
                    .withClaim("auth", "user")
                    .withClaim("email", email)
                    .withClaim("user_id",id)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .withIssuer("animals-shelter-2/project")
                    .sign(Algorithm.HMAC256(secret));
        }

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
