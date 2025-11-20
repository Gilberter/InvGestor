package com.invgestorback.controller;

import com.invgestorback.config.JwUtil;
import com.invgestorback.model.Role;
import com.invgestorback.model.User;
import com.invgestorback.repository.RoleRepository;
import com.invgestorback.repository.UserRepository;
import com.invgestorback.service.AuthService;
import com.invgestorback.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private RoleRepository roleRepository;
    @MockitoBean
    private UserRepository userRepository;

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
        mockMvc.perform(get("/auth/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("pong"));
    }

    @Test
    void testRegisterEndpoint() throws Exception {
        // Use the mocked service that AuthController calls for registration
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



}
