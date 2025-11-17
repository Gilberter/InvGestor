package com.invgestorback.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.invgestorback.EnumStates.PurschasingState;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Purchasing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String supplierName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "purchasing", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PurchasingItem> purchasingItems = new ArrayList<>();

    private long total;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private PurschasingState purschasingState;

    public Purchasing() {}

    public Purchasing(String supplierName, User user, List<PurchasingItem> purchasingItems) {
        this.supplierName = supplierName;
        this.user = user;
        this.date = LocalDateTime.now();
        if (purchasingItems != null) {
            purchasingItems.forEach(this::addPurchasingItem
            );
        }
    }
    public void addPurchasingItem(PurchasingItem purchasingItem) {
        purchasingItems.add(purchasingItem);
    }
    public void removePurchasingItem(PurchasingItem purchasingItem) {
        purchasingItems.remove(purchasingItem);
    }
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public void setPurchasingItems(List<PurchasingItem> purchasingItems) {
        for (PurchasingItem purchasingItem : new ArrayList<>(this.purchasingItems)) {
            removePurchasingItem(purchasingItem);
        }

        if (purchasingItems != null) {
            for(PurchasingItem purchasingItem : purchasingItems) {
                addPurchasingItem(purchasingItem);
            }

        }
    }

    public void setTotal(long total) {
        this.total = total;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setPurchasingState(PurschasingState purchasingState) {
        this.purschasingState = purchasingState;
    }
    public long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public long getTotal() {
        return total;
    }
    public long recalculateTotal() {
        return purchasingItems.stream()
                .mapToLong(item -> (long)(item.getPrice() * item.getQuantity()))
                .sum();
    }



}
