package com.invgestorback.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.invgestorback.EnumStates.PurschasingState;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Purchasing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    // private Product product
    private String supplierName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private long quantity;
    private long unitPrice;
    private long Total;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant creationDate;

    @Enumerated(EnumType.STRING)
    private PurschasingState purschasingState;

    public Purchasing() {}

    public Purchasing(String supplierName, long quantity, long unitPrice, long total, PurschasingState purschasingState) {
        this.supplierName = supplierName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.Total = total;
        this.purschasingState = purschasingState;

    }



}
