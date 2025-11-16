package com.invgestorback.service;

import com.invgestorback.config.JwUtil;
import com.invgestorback.model.BussinessSetUp;
import com.invgestorback.model.User;
import com.invgestorback.repository.BussinessSetUpRepository;
import com.invgestorback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BussinessSetUpRepository bussinessSetUpRepository;
    @Autowired
    private JwUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        // 1️⃣ Try finding a User first
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            }

            // Generate JWT for user
            System.out.println(user.getPassword());

            return jwtUtil.generateToken(user.getEmail(), user.getRoleNames());
        }

        // 2️⃣ Try finding a Business if not user
        Optional<BussinessSetUp> businessOpt = bussinessSetUpRepository.findByEmailResponsible(email);
        if (businessOpt.isPresent()) {
            BussinessSetUp business = businessOpt.get();

            if (!passwordEncoder.matches(password, business.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            }

            // Generate JWT for business (no roles, or use a default)
            return jwtUtil.generateToken(business.getEmailResponsible(), business.getRoleName());
        }

        // 3️⃣ None found
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}
