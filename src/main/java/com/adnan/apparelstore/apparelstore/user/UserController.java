package com.adnan.apparelstore.apparelstore.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/users/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @PostMapping("/users/{email}/cart/{sku}")
    public String addToCart(@PathVariable String email, @PathVariable String sku) {
        return userService.addToCart(email, sku);
    }

    @DeleteMapping("/users/{email}/cart/{sku}")
    public String removeFromCart(@PathVariable String email, @PathVariable String sku) {

        return userService.removeFromCart(email, sku);
    }

    @PutMapping("/users/{email}/cart/{sku}")
    public String updateCart(@PathVariable String email, @PathVariable String sku) {

        return userService.updateCart(email, sku);
    }

    @GetMapping("/users/{email}/cart")
    public List<CartItem> getUserCart(@PathVariable String email) {
        return userService.getUserCart(email);
    }

    // @GetMapping("/users/{email}/cart")
    // public ResponseEntity<Map<Product,Integer>> getUserCart(@PathVariable String
    // email) {
    // User user = userRepository.findById(email).get();
    // Map<String,Integer> cart = user.getCart();

    // Map<Product,Integer> cartProducts = new HashMap<Product,Integer>();

    // for (Map.Entry<String,Integer> entry : cart.entrySet()) {
    // String sku = entry.getKey();
    // Integer quantity = entry.getValue();

    // Optional<Product> productOptional = productRepository.findById(sku);
    // if (productOptional.isPresent()) {
    // Product product = productOptional.get();
    // cartProducts.put(product, quantity);
    // }
    // }

    // return ResponseEntity.ok(cartProducts);

    // }

    // @PostMapping("/users/{email}/cart")
    // public ResponseEntity<List<CartItem>> addToCart(@PathVariable String email,
    // @RequestParam String sku) {
    // User user = userRepository.findById(email).get();
    // List<CartItem> cart = user.getCart();

    // Optional<Product> productOptional = productRepository.findById(sku);
    // if (productOptional.isPresent()) {
    // Product product = productOptional.get();
    // CartItem cartItem = new CartItem(product, 1);
    // cart.add(cartItem);
    // }

    // user.setCart(cart);
    // userRepository.save(user);
    // return ResponseEntity.ok(user.getCartItems());
    // }

}
