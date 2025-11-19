package com.invgestorback.controller;

import com.invgestorback.config.JwUtil;
import com.invgestorback.model.BussinessSetUp;
import com.invgestorback.service.BussinessSetUpService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    public final BussinessSetUpService bussinessSetUpService;
    private final JwUtil jwUtil;

    public HomeController(BussinessSetUpService bussinessSetUpService, JwUtil jwUtil) {
        this.bussinessSetUpService = bussinessSetUpService;
        this.jwUtil = jwUtil;
    }
    public SecretKey key = Jwts.SIG.HS512.key().build();
    @GetMapping("/auth/secret")
    public String home() {
        String secret = Encoders.BASE64.encode(key.getEncoded());
        return " " +secret + " len" + secret.length();
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/test-authorization")
    public ResponseEntity<Map<String, Object>> testAuthorization(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        Claims claims = jwUtil.parseAndVerifyToken(token);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        String email = claims.getSubject();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Token is valid");
        response.put("email", email);
        response.put("roles", claims.get("roles"));
        response.put("issuedAt", claims.getIssuedAt());
        response.put("expiration", claims.getExpiration());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/register-business")
    public BussinessSetUp registerBussiness(@RequestBody HomeController.BussinesRegister request) {
        return bussinessSetUpService.registerBussinessSetUp(request.getEmail_responsible(),request.getPassword(),request.getBussiness_name(),request.getId_tributaria(),request.getName_responsible(),request.getCc_responsible());
    }

    public static class BussinesRegister {
        private Long id_tributaria;
        private String bussiness_name;
        private String password;
        private String name_responsible;
        private String cc_responsible;
        private String email_responsible;

        public Long getId_tributaria() {
            return id_tributaria;
        }
        public String getBussiness_name() {
            return bussiness_name;
        }
        public String getPassword() {
            return password;
        }
        public String getName_responsible() {
            return name_responsible;
        }
        public String getCc_responsible() {
            return cc_responsible;
        }
        public String getEmail_responsible() {
            return email_responsible;
        }
    }

}