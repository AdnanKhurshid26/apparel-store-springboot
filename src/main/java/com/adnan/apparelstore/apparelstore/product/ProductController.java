package com.adnan.apparelstore.apparelstore.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    //get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        
        return ResponseEntity.ok(productRepository.findAll());
    }

    //get product by sku
    @GetMapping("products/{sku}")
    public ResponseEntity<Product> getProductBySku(@PathVariable String sku) {
        return ResponseEntity.ok(productRepository.findById(sku).get());
    }

    //add product
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    //delete product by sku
    @DeleteMapping("products/{sku}")
    public void deleteProduct(@PathVariable String sku) {
        productRepository.deleteById(sku);
    }
    //update product by sku
    @PutMapping("/products")
    public ResponseEntity<Product> updateProduct( @RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

}
