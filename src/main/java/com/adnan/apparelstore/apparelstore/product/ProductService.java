package com.adnan.apparelstore.apparelstore.product;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.adnan.apparelstore.apparelstore.user.User;
import com.adnan.apparelstore.apparelstore.user.UserService;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getPaginatedProducts(int page, int size) {

        Page<Product> products = productRepository.findAll(PageRequest.of(page, size));

        return products;
    }

    public User getUserProfile(Principal principal) {
        String email = principal.getName();

        return userService.getUserByEmail(email);
    }

    public Product getProductBySKU(String sku) {
        return productRepository.findBySKU(sku);
    }

    public Product addProduct(Product product) {

        if (productRepository.findById(product.getSKU()).isPresent()) {
            return null;
        }
        return productRepository.save(product);
    }

    public void deleteProduct(String sku) {
        productRepository.deleteBySKU(sku);
    }

    public Product updateProduct(Product updatedProduct) {
        Product existingProduct = productRepository.findBySKU(updatedProduct.getSKU());
        if (existingProduct != null) {
            updatedProduct.setId(existingProduct.getId());
            return productRepository.save(updatedProduct);
        } else {
            return null;
        }
    }

}
