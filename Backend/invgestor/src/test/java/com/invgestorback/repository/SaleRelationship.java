package com.invgestorback.repository;

import com.invgestorback.model.Product;
import com.invgestorback.model.Sale;
import com.invgestorback.model.SaleItem;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // <-- Add this line
@Transactional
public class SaleRelationship {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaleWithItemsAndProducts() {
        // 1️⃣ Create product
        Product p1 = new Product();
        p1.setNameProduct("Keyboard");
        p1.setStockQuantity(20);
        p1.setUnitPrice(50.0);
        productRepository.save(p1);

        // 2️⃣ Create sale and link items
        Sale sale = new Sale();

        SaleItem item = new SaleItem();
        item.setProduct(p1);
        item.setQuantity(2);
        item.setPrice(50.0);
        item.setSale(sale);

        sale.getSaleItems().add(item);

        // 3️⃣ Persist the sale (should cascade to items)
        saleRepository.save(sale);

        // 4️⃣ Verify sale was saved and cascade worked
        Sale savedSale = saleRepository.findById(sale.getId()).orElseThrow();
        Assertions.assertEquals(1, savedSale.getSaleItems().size(), "Sale should have 1 item");

        // 5️⃣ Verify product link
        SaleItem savedItem = savedSale.getSaleItems().get(0);
        Assertions.assertEquals("Keyboard", savedItem.getProduct().getNameProduct(), "Product name should match");
    }
}
