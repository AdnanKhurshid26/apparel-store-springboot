package com.adnan.apparelstore.apparelstore.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    private static List<Product> products = new ArrayList<>();

    static{
        //Initialize Data
        products.add(new Product("SKU-1", 10, "Product 1", "Short Description 1", "Description 1", 100, null, null, null, null));
        products.add(new Product("SKU-2", 20, "Product 2", "Short Description 2", "Description 2", 200, null, null, null, null));
        products.add(new Product("SKU-3", 30, "Product 3", "Short Description 3", "Description 3", 300, null, null, null, null));
        products.add(new Product("SKU-4", 40, "Product 4", "Short Description 4", "Description 4", 400, null, null, null, null));
        products.add(new Product("SKU-5", 50, "Product 5", "Short Description 5", "Description 5", 500, null, null, null, null));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductBySku(String sku) {
        return products.stream().filter(p -> p.getSku().equals(sku)).findFirst().get();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void updateProduct(String sku, Product product) {

        for(int i=0; i<products.size(); i++){
            Product p = products.get(i);
            if(p.getSku().equals(sku)){
                products.set(i, product);
                return;
            }
        }
        
    }

    public void deleteProduct(String sku) {
        products.removeIf(p -> p.getSku().equals(sku));
    }
}
