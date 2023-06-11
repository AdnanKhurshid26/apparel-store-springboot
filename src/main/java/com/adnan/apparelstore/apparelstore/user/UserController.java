package com.adnan.apparelstore.apparelstore.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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



    @PostMapping("/users/{email}/cart/{sku}")
    public ResponseEntity<String> addToCart(@PathVariable String email, @PathVariable String sku) {
        User user = userRepository.findById(email).get();
        List<CartItem> cart = user.getCart();

        Optional<Product> productOptional = productRepository.findById(sku);     
        
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            String productsku = product.getSku();
            boolean found = false;

            if (cart == null) {
                cart = new ArrayList<CartItem>();
            }

            for (CartItem cartItem : cart) {
                if (cartItem.getSku().equals(productsku)) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                CartItem cartItem = new CartItem(productsku,product.getName(),product.getImages().get(0),product.getPrice(),1);
                cart.add(cartItem);
            }

            user.setCart(cart);

            userRepository.save(user);

            return ResponseEntity.ok("Added to cart");

        }

        return ResponseEntity.ok("Product not found");
    }

    @DeleteMapping("/users/{email}/cart/{sku}")
    public ResponseEntity<String> removeFromCart(@PathVariable String email, @PathVariable String sku) {
        User user = userRepository.findById(email).get();
        List<CartItem> cart = user.getCart();

        if (cart == null) {
            return ResponseEntity.ok("Cart is empty");
        }

        for (CartItem cartItem : cart) {
            if (cartItem.getSku().equals(sku)) {
                cart.remove(cartItem);
                break;
            }
        }

        user.setCart(cart);

        userRepository.save(user);

        return ResponseEntity.ok("Removed from cart");
    }

    @PutMapping("/users/{email}/cart/{sku}")
    public ResponseEntity<String> updateCart(@PathVariable String email, @PathVariable String sku) {
        User user = userRepository.findById(email).get();
        List<CartItem> cart = user.getCart();

        if (cart == null) {
            return ResponseEntity.ok("Cart is empty");
        }

        for (CartItem cartItem : cart) {
            if (cartItem.getSku().equals(sku)) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                if (cartItem.getQuantity() == 0) {
                    cart.remove(cartItem);
                }
                break;
            }
        }

        user.setCart(cart);

        userRepository.save(user);

        return ResponseEntity.ok("Cart updated");
    }


    @GetMapping("/users/{email}/cart")
    public ResponseEntity<List<CartItem>> getUserCart(@PathVariable String email) {
        User user = userRepository.findById(email).get();
        List<CartItem> cart = user.getCart();
       
        return ResponseEntity.ok(cart);
    }

    // @GetMapping("/users/{email}/cart")
    // public ResponseEntity<Map<Product,Integer>> getUserCart(@PathVariable String email) {
    //     User user = userRepository.findById(email).get();
    //     Map<String,Integer> cart = user.getCart();
   
    //     Map<Product,Integer> cartProducts = new HashMap<Product,Integer>();
    
    //     for (Map.Entry<String,Integer> entry : cart.entrySet()) {
    //         String sku = entry.getKey();
    //         Integer quantity = entry.getValue();
            
    //         Optional<Product> productOptional = productRepository.findById(sku);
    //         if (productOptional.isPresent()) {
    //             Product product = productOptional.get();
    //             cartProducts.put(product, quantity);
    //         }
    //     }
    
    //     return ResponseEntity.ok(cartProducts);

    // }   

    // @PostMapping("/users/{email}/cart")
    // public ResponseEntity<List<CartItem>> addToCart(@PathVariable String email, @RequestParam String sku) {
    //     User user = userRepository.findById(email).get();
    //     List<CartItem> cart = user.getCart();
        
    //     Optional<Product> productOptional = productRepository.findById(sku);
    //     if (productOptional.isPresent()) {
    //         Product product = productOptional.get();
    //         CartItem cartItem = new CartItem(product, 1);
    //         cart.add(cartItem);
    //     }

    //     user.setCart(cart);
    //     userRepository.save(user);
    //     return ResponseEntity.ok(user.getCartItems());
    // }

    
    
}
