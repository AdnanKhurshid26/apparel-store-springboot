package com.adnan.apparelstore.apparelstore.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    // get all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{page}/{size}")
    public Page<Product> getPaginatedProducts(@PathVariable int page, @PathVariable int size) {
        return productService.getPaginatedProducts(page, size);
    }

    // get product by sku
    @GetMapping("/products/{sku}")
    public Product getProductBySKU(@PathVariable String sku) {
        return productService.getProductBySKU(sku);
    }

    // add product
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {

        Product addedProduct = productService.addProduct(product);

        if (addedProduct == null) {
            return null;
        }

        return addedProduct;

    }

    // delete product by sku
    @DeleteMapping("/products/{sku}")
    public void deleteProduct(@PathVariable String sku) {
        productService.deleteProduct(sku);
    }

    // update product by sku
    @PutMapping("/products")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

}
