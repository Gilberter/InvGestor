package com.invgestorback.service;

import com.invgestorback.EnumStates.ProductState;
import com.invgestorback.EnumStates.SaleState;
import com.invgestorback.model.Product;
import com.invgestorback.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addNewProduct(String nameProduct, String descriptionProduct, Double unitPrice, Long limitStockQuantity){

        Product productExists = productRepository.findByNameProduct(nameProduct).orElseGet(
                () -> {
                    Product product = new Product();
                    product.setNameProduct(nameProduct);
                    product.setDescriptionProduct(descriptionProduct);
                    product.setUnitPrice(unitPrice);
                    product.setImageProduct(Boolean.FALSE);
                    product.setLimitStockQuantity(limitStockQuantity);
                    return productRepository.save(product);
                }
        );
        return productExists;



    }

    public Product addExistingProduct(Long idProduct, long quantity){
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new RuntimeException("No existe un producto con el id"));
        product.addQuantityProduct(quantity);
        product.setStateCalculator();
        return productRepository.save(product);

    }

}
