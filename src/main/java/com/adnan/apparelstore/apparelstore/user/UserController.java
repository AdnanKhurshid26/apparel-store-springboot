package com.adnan.apparelstore.apparelstore.user;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes({ "username" })
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('USER')")
    public String profile(Model model, Principal principal) {

        // call api /products to get user profile
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    @PreAuthorize("hasAuthority('USER')")
    public String updateProfile(@RequestParam String email, @RequestParam String name, @RequestParam String address) {

        // call api /products to get user profile
        userService.updateProfile(email, name, address);
        return "redirect:/profile";
    }

    @PostMapping("/add-to-cart")
    @PreAuthorize("hasAuthority('USER')")
    public String addToCart(Model model, @RequestParam String SKU, @RequestParam int page, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        userService.addToCart(principal.getName(), SKU);
        return "redirect:/?page=" + page;
    }

    @PostMapping("/remove-from-cart")
    @PreAuthorize("hasAuthority('USER')")
    public String removeFromCart(@RequestParam String sku, Model model, Principal principal) {

        userService.removeFromCart(principal.getName(), sku);
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    @PreAuthorize("hasAuthority('USER')")
    public String cart(Model model, Principal principal) {

        // call api /products to get user profile
        List<CartItem> cart = userService.getUserCart(principal.getName());
        int total = userService.getCartTotal(cart);
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @GetMapping("/inc-cart-qty")
    @PreAuthorize("hasAuthority('USER')")
    public String incCartQty(@RequestParam String sku, Model model, Principal principal) {
        String message = userService.addToCart(principal.getName(), sku);
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("message", message);
        return "redirect:/cart";
    }

    @GetMapping("/dec-cart-qty")
    @PreAuthorize("hasAuthority('USER')")
    public String decCartQty(@RequestParam String sku, Model model, Principal principal) {
        String message = userService.updateCart(principal.getName(), sku);
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("message", message);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasAuthority('USER')")
    public String checkout(Model model, Principal principal) {

        String username = principal.getName();

        userService.check(username);

        return "redirect:/";

    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user) {

        userService.addUser(user);

        return "redirect:/";
    }

    ////////////////////////////////////////////////////////////////////////////

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
}
