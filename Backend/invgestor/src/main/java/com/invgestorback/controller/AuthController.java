package com.invgestorback.controller;

import com.invgestorback.model.User;
import com.invgestorback.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    public final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestParam String email, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName) {
        return userService.registerUser(email, password, firstName, lastName);
    }
}
