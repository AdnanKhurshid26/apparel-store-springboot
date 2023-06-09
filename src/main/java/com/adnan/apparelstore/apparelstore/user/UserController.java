package com.adnan.apparelstore.apparelstore.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adnan.apparelstore.apparelstore.product.Product;
import com.adnan.apparelstore.apparelstore.product.ProductRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userRepository.findById(email).get());
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/users/{email}")
    public void deleteUser(@PathVariable String email) {
        userRepository.deleteById(email);
    }

    @GetMapping("/users/{email}/cart")
    public ResponseEntity<Map<Product,Integer>> getUserCart(@PathVariable String email) {
        User user = userRepository.findById(email).get();
        System.out.println(user);
        Map<String,Integer> cart = user.getCart();
        System.out.println(cart);
        Map<Product,Integer> cartProducts = new HashMap<Product,Integer>();
        System.out.println(cartProducts);
        for (Map.Entry<String,Integer> entry : cart.entrySet()) {
            String sku = entry.getKey();
            Integer quantity = entry.getValue();
            
            Optional<Product> productOptional = productRepository.findById(sku);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                cartProducts.put(product, quantity);
            }
        }
    
        return ResponseEntity.ok(cartProducts);

    }   

    @PostMapping("/users/{email}/cart")
    public ResponseEntity<Map<String,Integer>> addToCart(@PathVariable String email, @RequestParam String sku) {
        User user = userRepository.findById(email).get();
        Map<String,Integer> cart = user.getCart();
        cart.put(sku, cart.getOrDefault(sku, 0) + 1);
        user.setCart(cart);
        userRepository.save(user);
        return ResponseEntity.ok(cart);
    }

    
    
}
