package com.invgestorback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class PurchasingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "purchasing_id")
    @JsonBackReference
    private Purchasing purchasing;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    private double price;

    public PurchasingItem() {}
    public PurchasingItem(Purchasing purchasing, Product product, int quantity, double price) {
        this.product = product;
        this.purchasing = purchasing;
        this.quantity = quantity;
        this.price = price;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Purchasing getPurchasing() {
        return purchasing;
    }
    public void setPurchasing(Purchasing purchasing) {
        this.purchasing = purchasing;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;

    }
    public void setPrice(double price) {
        this.price = price;
    }
}
