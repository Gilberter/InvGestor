package com.invgestorback.service;

import com.invgestorback.EnumStates.PurschasingState;
import com.invgestorback.controller.SalePurchasingController;
import com.invgestorback.model.Product;
import com.invgestorback.model.Purchasing;
import com.invgestorback.model.PurchasingItem;
import com.invgestorback.model.User;
import com.invgestorback.repository.ProductRepository;
import com.invgestorback.repository.PurchasingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

public class PurchasingService {

    private final ProductRepository productRepository;
    private final PurchasingRepository purchasingRepository;
    public PurchasingService (ProductRepository productRepository, PurchasingRepository purchasingRepository) {
        this.productRepository = productRepository;
        this.purchasingRepository = purchasingRepository;
    }

}
