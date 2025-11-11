package com.invgestorback.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.invgestorback.model.*;
import com.invgestorback.repository.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Value("${jwt.secret}")
    private String jwtSecret;

    public SecurityConfig() {

    }

    // ✅ Define SecretKey Bean
    @Bean
    public SecretKey secretKey(@Value("${jwt.secret}") String jwtSecret) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ✅ Define Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ JWT utility bean
    @Bean
    public JwUtil jwUtil(SecretKey secretKey) {
        System.out.println("Iniciando JwUtil con secret codificado: " +
                Encoders.BASE64.encode(jwtSecret.getBytes(StandardCharsets.UTF_8)));
        return new JwUtil(secretKey);
    }

    // ✅ Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/home/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
