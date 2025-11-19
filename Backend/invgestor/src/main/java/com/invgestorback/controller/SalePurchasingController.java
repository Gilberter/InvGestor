package com.invgestorback.controller;

import com.invgestorback.EnumStates.PurschasingState;
import com.invgestorback.EnumStates.SaleState;
import com.invgestorback.config.JwUtil;
import com.invgestorback.model.*;
import com.invgestorback.repository.*;
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
import java.util.Map;

@RestController
@RequestMapping("/sale-purchasing")
@PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYED')")  // Changed to hasAnyRole
public class SalePurchasingController {

    public final UserRepository userRepository;
    public final SaleRepository saleRepository;
    public final JwUtil jwUtil;
    private final ProductRepository productRepository;
    private final SaleItemRepository saleItemRepository;
    private final PurchasingRepository purchasingRepository;

    public SalePurchasingController(UserRepository userRepository, SaleRepository saleRepository, JwUtil jwUtil, ProductRepository productRepository, SaleItemRepository saleItemRepository, PurchasingRepository purchasingRepository) {
        this.userRepository = userRepository;
        this.saleRepository = saleRepository;
        this.jwUtil = jwUtil;
        this.productRepository = productRepository;
        this.saleItemRepository = saleItemRepository;
        this.purchasingRepository = purchasingRepository;
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

    @PostMapping("/purchasing-user")
    public ResponseEntity<?> savePurchase(
            @RequestBody PurchasingRequest purchasingRequest,
            @RequestHeader("Authorization") String authHeader) {

        try {
            // 1️⃣ Validar header de autorización
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Missing or invalid Authorization header"));
            }

            String token = authHeader.substring(7);

            // 2️⃣ Verificar y parsear token
            Claims claims = jwUtil.parseAndVerifyToken(token);
            if (claims == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid or expired token"));
            }

            // 3️⃣ Obtener usuario
            String email = claims.getSubject();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            // 4️⃣ Validar request
            if (purchasingRequest.getPurchasingItems() == null || purchasingRequest.getPurchasingItems().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Purchasing items cannot be empty"));
            }

            // 5️⃣ Crear y guardar purchasing
            Purchasing savedPurchasing = createAndSavePurchasing(purchasingRequest, user);

            // 6️⃣ Actualizar stock de productos
            updateProductStock(purchasingRequest.getPurchasingItems());

            return new ResponseEntity<>(savedPurchasing, HttpStatus.CREATED);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error"));
        }
    }

    private Purchasing createAndSavePurchasing(PurchasingRequest purchasingRequest, User user) {
        Purchasing purchasing = new Purchasing();
        purchasing.setSupplierName(purchasingRequest.getSupplierName());
        purchasing.setUser(user);
        purchasing.setDate(LocalDateTime.now());

        // Crear items
        for (PurchasingItemRequest itemReq : purchasingRequest.getPurchasingItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Product not found with id: " + itemReq.getProductId()));

            PurchasingItem purchasingItem = new PurchasingItem();
            purchasingItem.setProduct(product);
            purchasingItem.setQuantity(itemReq.getQuantity());
            purchasingItem.setPrice(itemReq.getPrice());
            purchasingItem.setPurchasing(purchasing);

            purchasing.addPurchasingItem(purchasingItem);
        }

        purchasing.setTotal(purchasing.recalculateTotal());
        purchasing.setPurchasingState(PurschasingState.RECEIVED);

        return purchasingRepository.save(purchasing);
    }

    private void updateProductStock(List<PurchasingItemRequest> purchasingItems) {
        for (PurchasingItemRequest item : purchasingItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found during stock update"));

            // Incrementar stock (en compras se suma al stock)
            long newStock = product.getStockQuantity() + item.getQuantity();
            product.setStockQuantity(newStock);
            productRepository.save(product);
        }
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

    public static class PurchasingItemRequest {
        private Long productId;
        private long quantity;
        private double price;

        // Constructors
        public PurchasingItemRequest() {}

        public PurchasingItemRequest(Long productId, int quantity, double price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        // Getters and Setters
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        public long getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }

    public static class PurchasingRequest {
        private String supplierName;
        private List<PurchasingItemRequest> purchasingItems;

        // Constructors
        public PurchasingRequest() {}

        public PurchasingRequest(String supplierName, List<PurchasingItemRequest> purchasingItems) {
            this.supplierName = supplierName;
            this.purchasingItems = purchasingItems;
        }

        // Getters and Setters
        public String getSupplierName() { return supplierName; }
        public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

        public List<PurchasingItemRequest> getPurchasingItems() { return purchasingItems; }
        public void setPurchasingItems(List<PurchasingItemRequest> purchasingItems) {
            this.purchasingItems = purchasingItems;
        }
    }




}
