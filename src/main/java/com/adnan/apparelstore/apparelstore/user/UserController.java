package com.adnan.apparelstore.apparelstore.user;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            return "redirect:/";
        }
        userService.addUser(user);

        return "redirect:/";
    }

}
