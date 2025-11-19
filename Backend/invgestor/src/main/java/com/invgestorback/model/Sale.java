package com.invgestorback.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.invgestorback.EnumStates.SaleState;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String clientName;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SaleItem> saleItems = new ArrayList<>();

    private LocalDateTime date; // Add default value
    private long total;
    @Enumerated(EnumType.STRING)
    private SaleState saleState;

    public Sale() {
    }
    public Sale(String clientName, SaleState saleState, List<SaleItem> saleItems, User user) {
        this.clientName = clientName;
        this.saleState = saleState;
        this.date = LocalDateTime.now();
        // Add all sale items properly
        if (saleItems != null) {
            saleItems.forEach(this::addSaleItem);
        }
        this.user = user;

    }
    public long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public SaleState getSaleState() {
        return this.saleState;
    }
    public void setSaleState(SaleState saleState) {
        this.saleState = saleState;
    }
    public List<SaleItem> getSaleItems() {
        return this.saleItems;
    }
    public void setSaleItems(List<SaleItem> items) {

        // Remove existing items properly
        for (SaleItem item : new ArrayList<>(this.saleItems)) {
            removeSaleItem(item);
        }

        // Add new items safely
        if (items != null) {
            for (SaleItem item : items) {
                addSaleItem(item);
            }
        }
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void addSaleItem(SaleItem saleItem) {
        saleItems.add(saleItem);
        saleItem.setSale(this);
    }
    public void removeSaleItem(SaleItem saleItem) {
        saleItems.remove(saleItem);
        saleItem.setSale(null);
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public long getTotal() {
        return total;
    }

    public long recalculateTotal() {
        return saleItems.stream()
                .mapToLong(item -> (long)(item.getPrice() * item.getQuantity()))
                .sum();
    }
}
