package com.invgestorback.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
public class HomeController {

    public SecretKey key = Jwts.SIG.HS512.key().build();
    @GetMapping("/auth/secret")
    public String home() {
        String secret = Encoders.BASE64.encode(key.getEncoded());
        return " " +secret + " len" + secret.length();
    }

}