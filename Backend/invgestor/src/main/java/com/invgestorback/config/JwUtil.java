package com.invgestorback.config;

import io.jsonwebtoken.*;


import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

public class JwUtil {
    private final SecretKey secretKey = Jwts.SIG.HS512.key().build(); //Signing key


    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email) // registered claim set to email
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusSeconds(3600)))
                .issuer("InvGestor")
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }
    public Claims parseAndVerifyToken(String token) {
        try{
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            System.err.println("Token expired"  + e.getMessage());
        } catch (JwtException e) {
            System.err.println("Parsing or signature validation failed "  + e.getMessage());
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
}
