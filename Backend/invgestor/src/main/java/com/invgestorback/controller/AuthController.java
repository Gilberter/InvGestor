package com.invgestorback.controller;

import com.invgestorback.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.invgestorback.repository.*;
import com.invgestorback.model.*;
import com.invgestorback.config.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    public final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwUtil jwUtil;
    private final AuthService authService;

    public AuthController(UserService userService, UserRepository userRepository, RoleRepository roleRepository, JwUtil jwUtil, AuthService authService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwUtil = jwUtil;
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.registerUser(
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()
        );
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        System.out.println("token: " + token);
        return token;
        //return userService.login(request.getEmail(),request.getPassword()).map(
        //        user -> jwUtil.generateToken(user.getEmail(), user.getRoleNames())
        //).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }





    @GetMapping("/ping")
    public String ping() {
        return "pong ✅";
    }



    // Clase interna para el request
    public static class RegisterRequest {
        private String email;
        private String password;
        private String firstName;
        private String lastName;

        // Getters y Setters
        public String getEmail() { return email; }

        public String getPassword() { return password; }

        public String getFirstName() { return firstName; }

        public String getLastName() { return lastName; }
    }

    public static class LoginRequest {
        private String email;
        private String password;
        public String getEmail() { return email; }
        public String getPassword() { return password; }

    }
}