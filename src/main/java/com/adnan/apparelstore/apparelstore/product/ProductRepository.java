package com.adnan.apparelstore.apparelstore.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findBySKU(String sku);

    Product deleteBySKU(String sku);

    Page<Product> findByNameContainingIgnoreCase(String query, Pageable pageable);
}
