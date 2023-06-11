package com.adnan.apparelstore.apparelstore;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.adnan.apparelstore.apparelstore.product.Product;
import com.adnan.apparelstore.apparelstore.user.CartItem;
import com.adnan.apparelstore.apparelstore.user.User;

@Controller
public class FrontController {

   
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String home(Model model) {



        ResponseEntity<List<Product>> response = restTemplate.exchange(
                "http://localhost:8080/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {});

                List<Product> products = response.getBody();

                model.addAttribute("products", products);

        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model) {

        //call api /products to get user profile

        ResponseEntity<User> response = restTemplate.exchange(
                "http://localhost:8080/users/adnan@gmail",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<User>() {});

                User user = response.getBody();

                model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String sku) {

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/users/adnan@gmail/cart/"+sku,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<String>() {});

                String message = response.getBody();

                System.out.println(message);
        return "redirect:/";
    }

    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam String sku) {

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/users/adnan@gmail/cart/"+sku,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<String>() {});

                String message = response.getBody();

                System.out.println(message);
        return "redirect:/cart";
    }
    
    @GetMapping("/cart")
    public String cart(Model model) {

        //call api /products to get user profile
        
        ResponseEntity<List<CartItem>> response = restTemplate.exchange(
                "http://localhost:8080/users/adnan@gmail/cart",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CartItem>>() {});

                List<CartItem> cart = response.getBody();

                int grandTotal = 0;

                if(cart != null) {
                    for(CartItem item : cart) {
                        grandTotal += item.getQuantity() * item.getPrice();
                    }
                }

                model.addAttribute("cart", cart);
                model.addAttribute("grandTotal", grandTotal);

            return "cart";
    }

    @GetMapping("/inc-cart-qty")
    public String incCartQty(@RequestParam String sku) {

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/users/adnan@gmail/cart/"+sku,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<String>() {});

                String message = response.getBody();

                System.out.println(message);
        return "redirect:/cart";
    }

    @GetMapping("/dec-cart-qty")
    public String decCartQty(@RequestParam String sku) {

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/users/adnan@gmail/cart/"+sku,
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {});

                String message = response.getBody();

                System.out.println(message);
        return "redirect:/cart";
    }
    
}
