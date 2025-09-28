package com.invgestorback.service;

import com.invgestorback.model.*;
import com.invgestorback.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register New User
    public User registerUser(String email, String rawPassword, String firstName, String lastName){
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacio");
        }
        if (rawPassword == null || rawPassword.trim().isEmpty() || rawPassword.length() < 8) {
            throw new IllegalArgumentException("La contraseña no puede estar vacio y debe tener almenos 8 caracteres");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("El Nombre no puede estar vacio");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("El Apellido no puede estar vacio");
        }
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("El email ya existe");

        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return userRepository.save(user);
    }

    // Login
    public Optional<User> login(String email, String rawPassword){
        if(!userRepository.existsByEmail(email)){ // Doesnt exists False !False = True
            throw new RuntimeException("El email no existe");
        }
        return userRepository.findByEmail(email).filter(
                user -> passwordEncoder.matches(rawPassword, user.getPassword())
        );
    }
}
