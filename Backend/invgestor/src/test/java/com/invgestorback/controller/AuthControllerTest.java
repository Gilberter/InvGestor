package com.invgestorback.controller;

import com.invgestorback.config.JwUtil;
import com.invgestorback.model.Role;
import com.invgestorback.model.User;
import com.invgestorback.repository.RoleRepository;
import com.invgestorback.repository.UserRepository;
import com.invgestorback.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwUtil jwUtil;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private RoleRepository roleRepository;
    @MockitoBean
    private UserService userService;

    private User mockUser;
    private Role mockRole;


    @BeforeEach
    void setUp() {

        mockUser = new User();
        mockRole = new Role();
        mockRole.setName("ADMIN");

        mockUser.setEmail("test@example.com");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setPassword("password");
        mockUser.setRoles(Set.of(mockRole));
    }

    @Test
    void testPingEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/ping"))
                .andExpect(status().isOk());
    }

    @Test
    void testRegisterEndpoint() throws Exception {
        Mockito.when(userService.registerUser(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(mockUser);

        String jsonRequest = """
                {
                    "email": "test@example.com",
                    "password": "1234",
                    "firstName": "John",
                    "lastName": "Doe"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON).
                content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                ;
    }

    @Test
    void testLoginEndpoint() throws Exception {
        String email = "test@example.com";
        String password = "password";
        Set<String> roles = Set.of("ADMIN");
        Mockito.when(userService.login(email,password)).thenReturn(Optional.of(mockUser));
        Mockito.when(jwUtil.generateToken(email,roles)).thenReturn("mocked-jwt-token");
        System.out.println();
        String loginJson = """
                {
                    "email": "test@example.com",
                    "password": "password"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginEndpointUnauthorized() throws Exception {

        Mockito.when(userService.login(anyString(),anyString())).thenReturn(Optional.empty());
        String loginJson = """
                {
                    "email": "bad@example.com",
                    "password": "1234"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                        .andExpect(status().isUnauthorized());
    }
}
