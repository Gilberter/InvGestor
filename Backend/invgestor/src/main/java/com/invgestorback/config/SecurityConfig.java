package com.invgestorback.config;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.invgestorback.model.*;
import com.invgestorback.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final RoleRepository roleRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecretKey secretKey(@Value("${jwt.secret}") String jwtSecret) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public SecurityConfig(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean //
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }



    @Bean
    CommandLineRunner initData(RoleRepository roleRepository, PermissionRepository permissionRepository, UserRepository userRepository) {
        return args -> {
            // 1. Define permissions
            List<String> allPermissions = List.of(
                    "CRUD_USERS", "CRUD_PRODUCT", "STOCK_ADMIN", "STOCK_MANAGER", "DASHBOARD"

            );

            for (String permName : allPermissions) {
                permissionRepository.findByName(permName)
                        .orElseGet(() -> permissionRepository.save(new Permission(permName)));
            }

            // Fetch all saved permissions
            List<Permission> savedPermissions = permissionRepository.findAll();

            // 2. Create roles

            createRoleIfNotExists("ADMIN", new HashSet<>(savedPermissions), roleRepository);
            createRoleIfNotExists("PROPIETARIO",
                    new HashSet<>(permissionRepository.findAll().stream()
                            .filter(p -> p.getName().startsWith("DASHBOARD"))
                            .toList()), roleRepository);
            createRoleIfNotExists("EMPLEADO",
                    new HashSet<>(permissionRepository.findAll().stream()
                            .filter(p -> p.getName().equals("STOCK_MANAGER"))
                            .toList()), roleRepository);


            createAdminUser(userRepository, passwordEncoder());
        };
    }

    private void createRoleIfNotExists(String roleName, Set<Permission> permissions, RoleRepository roleRepository) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role(roleName);
            role.setPermissions(permissions);
            return roleRepository.save(role);
        });
    }

    private void createAdminUser(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder
                                ) {
        String adminEmail = "admin@invgestor.com";
        Role role = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("El rol no existe"));
        Optional<User> existingAdmin = userRepository.findByEmail(adminEmail);
        if (existingAdmin.isEmpty()) {
            User admin = new User();
            admin.setFirstName("Administrador");
            admin.setLastName("Sistema");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123")); // Default password
            admin.getRoles().add(role);


            userRepository.save(admin);
            System.out.println("✅ Usuario administrador creado:");
            System.out.println("   Email: " + adminEmail);
            System.out.println("   Password: admin123");
            System.out.println("   Rol: " + role.getName());
        } else {
            System.out.println("ℹ️  Usuario administrador ya existe: " + adminEmail);
        }
    }

    @Bean
    public JwUtil jwUtil(SecretKey secretKey) {
        System.out.println("Iniciando JwUtil" + Encoders.BASE64.encode(jwtSecret.getBytes(StandardCharsets.UTF_8)));
        return new JwUtil(secretKey);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // register/login public// Allow access
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
