package com.invgestorback.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    // Middleware Spring Security Filter, check every incoming HTTP request
    // for a JWT and authenticates the user if the token is valid
    private final JwUtil jwUtil;

    public JwtAuthFilter(JwUtil jwUtil) {
        this.jwUtil = jwUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
                                    // Request, Response, Next
        String authHeader = request.getHeader("Authorization"); // Extracts a JWT from the authorization header

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Authorization: Bearer <JWT_TOKEN> cuts only the token
            String token = authHeader.substring(7);

            Claims claims = jwUtil.parseAndVerifyToken(token);

            if (claims != null) {
                String email = claims.getSubject();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                // SecurityContextHolder.getContext().getAuthentication().getName(); get the logged-in user's email
            }
        }

        filterChain.doFilter(request, response);
    }
}

