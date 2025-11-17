package com.invgestorback.controller;

import com.invgestorback.EnumStates.ProductState;
import com.invgestorback.model.Product;
import com.invgestorback.model.User;
import com.invgestorback.repository.RoleRepository;
import com.invgestorback.repository.UserRepository;
import com.invgestorback.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.invgestorback.service.UserService;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")  // Changed to hasAnyRole
public class AdminController {

    public final UserService userService;
    public final RoleRepository roleRepository;
    private final ProductService productService;

    public AdminController(UserService userService, RoleRepository roleRepository, ProductService productService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.productService = productService;
    }
    @GetMapping("/remove-user")
    public String removeUser() {
        return  "UserRemoved";
    }

    @PostMapping("/assign-role")
    public ResponseEntity<ResponseMessageUserService> assignRole(@RequestBody AdminController.RoleRequest request) {
        User user = userService.assignRoleToUser(request.getEmail(), request.getRolename());
        ResponseMessageUserService response;
        if (user == null) {
            response = new ResponseMessageUserService("No se encontro usuario",null,null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(new ResponseMessageUserService("Se asigno rol",user.getEmail(),user.getRoleNamesList()));
    }

    @PostMapping("/remove-rol")
    public ResponseEntity<ResponseMessageUserService> removeRole(@RequestBody AdminController.RoleRequest request) {
        User user = userService.deleteRoleFromUser(request.getEmail(), request.getRolename());
        ResponseMessageUserService response;
        System.out.println("hola");
        if (user == null) {
            response = new ResponseMessageUserService("No se encontro usuario",null,null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(new ResponseMessageUserService("Se elimino rol",user.getEmail(),user.getRoleNamesList()));
    }

    @PostMapping("/add-newProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest request) {
        Product response = productService.addNewProduct(request.getNameProduct(),request.getDescriptionProduct(),request.getUnitPrice(), request.getLimitStockQuantity());
        if (response == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Unable to create product — invalid data or product already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        Map<String, Object> success = new HashMap<>();
        success.put("success", true);
        success.put("data", response);
        return ResponseEntity.ok(success);
    }

    @PostMapping("/add-ExistingProduct")
    public Product addExistingProduct(@RequestBody EditProductRequest request) {
        return productService.addExistingProduct(request.getIdProduct(), request.getAddQuantityProduct());

    }

    @PostMapping("/register")
    public User register(@RequestBody AuthController.RegisterRequest request) {
        return userService.registerUser(
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()
        );
    }

    @PostMapping("/test")
    public String test() {
        return "test";
    }
    public static class ProductRequest {
        private String nameProduct;
        private String descriptionProduct;
        private Double unitPrice;
        private Long limitStockQuantity;

        public String getNameProduct() {
            return nameProduct;
        }
        public String getDescriptionProduct() {
            return descriptionProduct;
        }
        public Double getUnitPrice() {
            return unitPrice;
        }
        public Long getLimitStockQuantity() {return limitStockQuantity;}

    }
    public static class ResponseMessageUserService {
        private String message;
        private String email;
        private List<String> roles = new ArrayList<>();

        public ResponseMessageUserService(String message, String email, List<String> roles) {
            this.message = message;
            this.email = email;
            this.roles = roles;
        }

        public String getMessage() {
            return message;
        }

        public String getEmail() {
            return email;
        }

        public List<String> getRoles() {
            return roles;
        }
    }


    public static class EditProductRequest {
        private long idProduct;
        private long addQuantityProduct;
        public long getIdProduct() {return idProduct;}
        public long getAddQuantityProduct() {return addQuantityProduct;}
    }
    public static class RoleRequest {
        private String email;
        private String rolename;
        public String getEmail() { return email; }

        public String getRolename() { return rolename; }


    }
}
