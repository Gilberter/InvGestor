package com.invgestorback.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.invgestorback.model.*;
import com.invgestorback.repository.*;
import org.springframework.boot.CommandLineRunner;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean //
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()); // Optional: disable CSRF for testing

        return http.build();
    }


    @Bean
    CommandLineRunner initData(RoleRepository roleRepository, PermissionRepository permissionRepository) {
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
        };
    }

    private void createRoleIfNotExists(String roleName, Set<Permission> permissions, RoleRepository roleRepository) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role(roleName);
            role.setPermissions(permissions);
            return roleRepository.save(role);
        });
    }


}
