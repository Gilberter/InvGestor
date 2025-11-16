package com.invgestorback.repository;

import com.invgestorback.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameProduct(String nameProduct);
    Optional<Product> findById(Long idProduct);
}
