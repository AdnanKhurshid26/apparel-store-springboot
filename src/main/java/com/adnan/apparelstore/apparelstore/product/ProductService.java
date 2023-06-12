package com.adnan.apparelstore.apparelstore.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductBySku(String sku) {
        return productRepository.findById(sku).get();
    }

    public Product addProduct(Product product) {

        if(productRepository.findById(product.getSku()).isPresent()) {
            return null;
        }
        return productRepository.save(product);
    }

    public void deleteProduct(String sku) {
        productRepository.deleteById(sku);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

}
