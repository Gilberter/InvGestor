package com.invgestorback.repository;

import com.invgestorback.model.Purchasing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasingRepository extends JpaRepository<Purchasing, Long> {
}
