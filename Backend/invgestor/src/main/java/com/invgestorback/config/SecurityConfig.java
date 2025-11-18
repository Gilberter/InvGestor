package com.invgestorback.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.invgestorback.model.*;
import com.invgestorback.repository.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Value("${jwt.secret}")
    private String jwtSecret;

    public SecurityConfig() {

    }

    // ✅ Define SecretKey Bean
    @Bean
    public SecretKey secretKey(@Value("${jwt.secret}") String jwtSecret) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ✅ Define Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ JWT utility bean
    @Bean
    public JwUtil jwUtil(SecretKey secretKey) {
        System.out.println("Iniciando JwUtil con secret codificado: " +
                Encoders.BASE64.encode(jwtSecret.getBytes(StandardCharsets.UTF_8)));
        return new JwUtil(secretKey);
    }

    // ✅ Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/home/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository, UserRepository userRepository, SaleItemRepository saleItemRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Define the role names you want in your system
            String[] roleNames = {"OWNER", "ADMIN", "EMPLOYED"};

            for (String roleName : roleNames) {
                // If not exists, create
                roleRepository.findByName(roleName)
                        .orElseGet(() -> {
                            Role role = new Role(roleName);
                            roleRepository.save(role);
                            System.out.println("✅ Role created: " + roleName);
                            return role;
                        });
            }

            String emailUser = "admin1234@gmail.com";
            String passwordUser = "admin123";

            userRepository.findByEmail(emailUser).orElseGet(
                    () -> {

                        List<String> roles = new ArrayList<>();
                        roles.add("ADMIN");
                        roles.add("EMPLOYED");
                        Set<Role> roleset = roleRepository.findRoleByNameIn(roles).orElseThrow(() -> new UsernameNotFoundException("No roles found"));
                        User user = new User("Juan","Vanegas",emailUser,passwordEncoder.encode(passwordUser),roleset);
                        userRepository.save(user);
                        return user;

                    }
            );

            String nameProduct = "Televisor";
            productRepository.findByNameProduct(nameProduct).orElseGet(
                    () -> {
                        Product product = new Product();
                        product.setNameProduct(nameProduct);
                        product.setUnitPrice(1000.0);
                        product.setLimitStockQuantity(10);
                        productRepository.save(product);
                        return product;
                    }
            );




        };
    }
}
