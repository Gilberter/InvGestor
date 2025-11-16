package com.invgestorback.controller;

import com.invgestorback.EnumStates.SaleState;
import com.invgestorback.config.JwUtil;
import com.invgestorback.model.Product;
import com.invgestorback.model.Sale;
import com.invgestorback.model.SaleItem;
import com.invgestorback.model.User;
import com.invgestorback.repository.ProductRepository;
import com.invgestorback.repository.SaleItemRepository;
import com.invgestorback.repository.SaleRepository;
import com.invgestorback.repository.UserRepository;
import com.invgestorback.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sale-purchasing")
@PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")  // Changed to hasAnyRole
public class SalePurchasingController {

    public final UserRepository userRepository;
    public final SaleRepository saleRepository;
    public final JwUtil jwUtil;
    private final ProductRepository productRepository;
    private final SaleItemRepository saleItemRepository;

    public SalePurchasingController(UserRepository userRepository, SaleRepository saleRepository, JwUtil jwUtil, ProductRepository productRepository, SaleItemRepository saleItemRepository) {
        this.userRepository = userRepository;
        this.saleRepository = saleRepository;
        this.jwUtil = jwUtil;
        this.productRepository = productRepository;
        this.saleItemRepository = saleItemRepository;
    }

    @PostMapping("/sale-user")
    public ResponseEntity<Sale> saveSale(@RequestBody SaleRequest saleRequest, @RequestHeader("Authorization") String authHeader) {
        // 1️⃣ Extract token from header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);

        // 2️⃣ Parse and verify token
        Claims claims = jwUtil.parseAndVerifyToken(token);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        // 3️⃣ Extract email
        String email = claims.getSubject();

        // 4️⃣ Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 5️⃣ Build Sale
        Sale sale = new Sale();
        sale.setClientName(saleRequest.getClientName());
        sale.setUser(user);
        sale.setDate(LocalDateTime.now());

        for (SaleItemRequest itemReq : saleRequest.getSaleItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            SaleItem saleItem = new SaleItem();
            saleItem.setProduct(product);
            saleItem.setQuantity(itemReq.getQuantity());
            saleItem.setPrice(itemReq.getPrice());

            sale.addSaleItem(saleItem);
        }
        sale.setTotal(sale.recalculateTotal());
        sale.setSaleState(SaleState.COMPLETED);
        Sale saved = saleRepository.save(sale);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);


    }


    // DTOs for request
    public static class SaleItemRequest {
        private Long productId;
        private int quantity;
        private double price;
        // getters and setters
        public Long getProductId() {
            return productId;
        }
        public int getQuantity() {
            return quantity;
        }
        public double getPrice() {
            return price;
        }
    }

    public static class SaleRequest {
        private String clientName;
        private List<SaleItemRequest> saleItems;
        private String saleState; // example: "COMPLETED" or "PENDING"
        // getters and setters
        public String getClientName() {
            return clientName;
        }

        public List<SaleItemRequest> getSaleItems() {
            return saleItems;
        }
        public String getSaleState() {
            return saleState;
        }

    }




}
