package com.invgestorback.model;

import com.invgestorback.EnumStates.ProductState;
import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameProduct;
    private String descriptionProduct;
    private Boolean imageProduct;
    private Double unitPrice;
    private long stockQuantity;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;
    @Enumerated(EnumType.STRING)
    private ProductState state;

    public Product() {}

    public Product(String name, String descriptionProduct, Boolean imageProduct, Double unitPrice, ProductState state, Long stockQuantity) {
        this.nameProduct = name;
        this.descriptionProduct = descriptionProduct;
        this.imageProduct = imageProduct;
        this.unitPrice = unitPrice;
        this.state = state;
        this.stockQuantity = stockQuantity;

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNameProduct() {
        return nameProduct;
    }
    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }
    public String getDescriptionProduct() {
        return descriptionProduct;
    }
    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }
    public Boolean getImageProduct() {
        return imageProduct;
    }
    public void setImageProduct(Boolean imageProduct) {
        this.imageProduct = imageProduct;
    }
    public Double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Double valueProduct) {
        this.unitPrice = valueProduct;
    }
    public ProductState getState() {
        return state;
    }
    public void setState(ProductState state) {
        this.state = state;
    }
    public void setStockQuantity(long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    public long getStockQuantity() {
        return stockQuantity;
    }


}
