package com.invgestorback.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.invgestorback.EnumStates.SaleState;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
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
    @OneToMany(mappedBy = "sale")
    private List<SaleItem> saleItems;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant date;
    private long total;
    @Enumerated(EnumType.STRING)
    private SaleState saleState;

    public Sale() {
        this.saleItems = new ArrayList<>();
    }
    public Sale(long id, String clientName, SaleState saleState, Instant date, long total, List<SaleItem> saleItems, User user) {
        this.id = id;
        this.clientName = clientName;
        this.saleState = saleState;
        this.date = date;
        this.total = total;
        this.saleItems = saleItems;
        this.user = user;

    }
    public long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public SaleState getSaleState() {
        return saleState;
    }
    public void setSaleState(SaleState saleState) {
        this.saleState = saleState;
    }
    public List<SaleItem> getItems() {
        return saleItems;
    }

}
