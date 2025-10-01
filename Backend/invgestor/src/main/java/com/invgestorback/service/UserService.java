package com.invgestorback.service;

import com.invgestorback.model.*;
import com.invgestorback.repository.UserRepository;
import com.invgestorback.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.invgestorback.config.*;

import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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

        Role defaultRole = roleRepository.findByName("EMPLEADO")
                .orElseThrow(() -> new RuntimeException("El rol EMPLEADO no existe."));

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setFirstName(firstName);
        user.setLastName(lastName);

        user.getRoles().add(defaultRole);
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

    // Assign-Role
    public User assignRoleToUser(String email, String rolename){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("El email no existe"));
        Role role = roleRepository.findByName(rolename).orElseThrow(() -> new RuntimeException("El rol no existe"));
        user.getRoles().add(role);
        return userRepository.save(user);

    }

    //delete-rol
    public User deleteRoleFromUser(String email, String rolename){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("El email no existe"));
        Role role = roleRepository.findByName(rolename).orElseThrow(() -> new RuntimeException("El rol no existe"));
        user.getRoles().remove(role);
        return userRepository.save(user);
    }
}
