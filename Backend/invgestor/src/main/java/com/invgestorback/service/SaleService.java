package com.invgestorback.service;

import com.invgestorback.config.JwUtil;
import com.invgestorback.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private JwUtil jwUtil;

}
