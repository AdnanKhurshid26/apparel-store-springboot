package com.adnan.apparelstore.apparelstore.frontend;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.adnan.apparelstore.apparelstore.product.Product;
import com.adnan.apparelstore.apparelstore.user.CartItem;
import com.adnan.apparelstore.apparelstore.user.User;

import jakarta.validation.Valid;


@Controller
public class FrontController {

   @Autowired
   private FrontService frontService;
    
   
    @GetMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public String home(Model model,@RequestParam(value = "size",defaultValue = "8") int size,@RequestParam(value = "page",defaultValue = "0") int page) {
       
        Page<Product> prods = frontService.getPaginatedProducts(page, size);
        List<Product> products = prods.getContent();
        int totalPages = prods.getTotalPages();
        int currentPage = prods.getNumber();
        System.out.println("totalPages: "+totalPages);
        System.out.println("currentPage: "+currentPage);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);   
        return "home";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('USER')")
    public String profile(Model model) {

        //call api /products to get user profile

        User user = frontService.getUserProfile();
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    @PreAuthorize("hasAuthority('USER')")
    public String updateProfile(@Valid User user) {

        //call api /products to get user profile
        frontService.updateProfile(user);
        return "redirect:/profile";
    }

    @PostMapping("/add-to-cart")
    @PreAuthorize("hasAuthority('USER')")
    public String addToCart(@RequestParam String SKU,@RequestParam int page) {

        frontService.addToCart(SKU);
        return "redirect:/?page="+page;
    }

    @PostMapping("/remove-from-cart")
    @PreAuthorize("hasAuthority('USER')")
    public String removeFromCart(@RequestParam String sku) {
        frontService.removeFromCart(sku);
        return "redirect:/cart";
    }
    
    @GetMapping("/cart")
    @PreAuthorize("hasAuthority('USER')")
    public String cart(Model model) {

        //call api /products to get user profile
        
        List<CartItem> cartitems = frontService.getCart();

        //print name of products referred to byb cart item

        //remove cartItem from cart if product is null

        List<CartItem> cart = frontService.removeNullCartItem(cartitems);

        int total =  frontService.getCartTotal(cart);

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
            return "cart";
    }

    @GetMapping("/inc-cart-qty")
    @PreAuthorize("hasAuthority('USER')")
    public String incCartQty(@RequestParam String sku,Model model) {
        String message = frontService.incCartQty(sku);
        model.addAttribute("message", message);
        return "redirect:/cart";
    }

    @GetMapping("/dec-cart-qty")
    @PreAuthorize("hasAuthority('USER')")
    public String decCartQty(@RequestParam String sku,Model model) {

        String message =  frontService.decCartQty(sku);        
        model.addAttribute("message", message);
        return "redirect:/cart";
    }


    //Admin routes

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin(Model model,@RequestParam(value = "size",defaultValue = "8") int size,@RequestParam(value = "page",defaultValue = "0") int page) {
       
        Page<Product> prods = frontService.getPaginatedProducts(page, size);
        List<Product> products = prods.getContent();
        int totalPages = prods.getTotalPages();
        int currentPage = prods.getNumber();
        System.out.println("totalPages: "+totalPages);
        System.out.println("currentPage: "+currentPage);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);   
        return "admin";
    }

    
    @PostMapping("/admin/delete-product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteProduct(@RequestParam String sku) {

        frontService.deleteProduct(sku);

        return "redirect:/admin";
    }

    
    @GetMapping("/admin/update-product/{sku}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addProduct(@PathVariable String sku,Model model) {

        Product product = frontService.getProductBySKU(sku);
        model.addAttribute("product", product);
        return "update-product";
    }

 
    @PostMapping("/admin/update-product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateProduct(@Valid Product product) {

        frontService.updateProduct(product);

        return "redirect:/admin";
    }


    @GetMapping("/admin/add-product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addProduct() {

        return "add-product";
    }
    
    @PostMapping("/admin/add-product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addProduct(@Valid Product product) {

         RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/products"; 
        HttpMethod httpMethod = HttpMethod.POST;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); 
        HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);


        ResponseEntity<Product> response = restTemplate.exchange(
                url,
                httpMethod,
                requestEntity,
                new ParameterizedTypeReference<Product>() {});


                Product message = response.getBody();

                System.out.println(message);

        return "redirect:/admin";
    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user) {

        frontService.addUser(user);

        return "redirect:/";
    }
}
