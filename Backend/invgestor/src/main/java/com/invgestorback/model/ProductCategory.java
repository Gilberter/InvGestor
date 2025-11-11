package com.invgestorback.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public ProductCategory() {}
    public ProductCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
