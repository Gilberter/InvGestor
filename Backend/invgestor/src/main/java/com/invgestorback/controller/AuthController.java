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

    public AuthController(UserService userService, UserRepository userRepository, RoleRepository roleRepository, JwUtil jwUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwUtil = jwUtil;
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
        return userService.login(request.getEmail(),request.getPassword()).map(
                user -> jwUtil.generateToken(user.getEmail())
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }



    @GetMapping("/ping")
    public String ping() {
        return "pong ✅";
    }

    @PostMapping("/assign-role")
    public User assignRole(@RequestBody AssignRoleRequest request) {
        return userService.assignRoleToUser(request.getEmail(), request.getRolename());
    }

    @PostMapping("/remove-rol")
    public User removeRole(@RequestBody AssignRoleRequest request) {
        return userService.deleteRoleFromUser(request.getEmail(), request.getRolename());
    }

    public static class AssignRoleRequest {
        private String email;
        private String rolename;
        public String getEmail() { return email; }

        public String getRolename() { return rolename; }


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