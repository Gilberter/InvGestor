package com.invgestorback.config;

import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;

public class JwtAuthFilterTest {
    @MockitoBean
    private JwUtil jwUtil;

    private JwtAuthFilter jwtAuthFilter;
    @BeforeEach
    void setUp() {
        jwUtil = Mockito.mock(JwUtil.class);
        jwtAuthFilter = new JwtAuthFilter(jwUtil);
        SecurityContextHolder.clearContext();
    }

}
