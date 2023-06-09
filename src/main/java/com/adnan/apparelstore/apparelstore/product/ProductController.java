package com.adnan.apparelstore.apparelstore.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        super();    
        this.productService = productService;
    }

    //get all products
    
    @GetMapping("/products")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //get product by sku

    @GetMapping("/products/{sku}")
    @ResponseBody
    public Product getProductById(@PathVariable String sku) {
        return productService.getProductBySku(sku);
    }

    //add product

    @PostMapping("/products")
    @ResponseBody
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    //delete product by sku

    @DeleteMapping("/products/{sku}")
    @ResponseBody
    public void deleteProduct(@PathVariable String sku) {
        productService.deleteProduct(sku);
    }

    //update product by sku

    @PutMapping("/products/{sku}")
    @ResponseBody
    public void updateProduct(@PathVariable String sku, @RequestBody Product product) {
        productService.updateProduct(sku, product);
    }


}
