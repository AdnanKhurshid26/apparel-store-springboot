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
    private ProductService productService;


    //get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //get product by sku
    @GetMapping("products/{sku}")
    public ResponseEntity<Product> getProductBySku(@PathVariable String sku) {
        return ResponseEntity.ok(productService.getProductBySku(sku));
    }

    //add product
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        Product addedProduct = productService.addProduct(product);

        if(addedProduct == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(addedProduct);
        
    }

    //delete product by sku
    @DeleteMapping("products/{sku}")
    public void deleteProduct(@PathVariable String sku) {
        productService.deleteProduct(sku);
    }
    //update product by sku
    @PutMapping("/products")
    public ResponseEntity<Product> updateProduct( @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

}
