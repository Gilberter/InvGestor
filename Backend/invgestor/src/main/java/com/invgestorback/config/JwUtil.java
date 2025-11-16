package com.invgestorback.config;

import com.invgestorback.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Component
public class JwUtil {

    public final SecretKey secretKey;

    public JwUtil(SecretKey secretKey) {
        this.secretKey = secretKey;
        System.out.println(secretKey);
    }
    public String generateToken(String email, Set<String> roles) {

        return Jwts.builder()
                .subject(email) // registered claim set to email
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusSeconds(3600)))
                .issuer("InvGestor")
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }
    public Claims parseAndVerifyToken(String token) {
        try{
            System.out.println("Iniciando JwUtil token" + token);
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            System.err.println("Token expired"  + e.getMessage());
        } catch (JwtException e) {
            System.err.println("Parsing or signature validation failed "  + e.getMessage() + "Secret Key" + secretKey.toString());
        }
        return null;
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }
}
