package com.adnan.apparelstore.apparelstore.product;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.adnan.apparelstore.apparelstore.user.User;

import jakarta.validation.Valid;

@Controller
@SessionAttributes({ "username" })
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public String home(Model model, @RequestParam(value = "size", defaultValue = "8") int size,
            @RequestParam(value = "page", defaultValue = "0") int page, Principal principal) {

        Page<Product> prods = productService.getPaginatedProducts(page, size);
        List<Product> products = prods.getContent();
        int totalPages = prods.getTotalPages();
        int currentPage = prods.getNumber();
        User user = productService.getUserProfile(principal);
        String username = user.getEmail();
        model.addAttribute("user", user);
        model.addAttribute("username", username);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("addSearchBar", true);
        return "home";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin(Model model, @RequestParam(value = "size", defaultValue = "8") int size,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<Product> prods = productService.getPaginatedProducts(page, size);
        List<Product> products = prods.getContent();
        int totalPages = prods.getTotalPages();
        int currentPage = prods.getNumber();
        System.out.println("totalPages: " + totalPages);
        System.out.println("currentPage: " + currentPage);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("addSearchBar", true);
        return "admin";
    }

    @PostMapping("/admin/delete-product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteProduct(@RequestParam String sku) {

        productService.deleteProduct(sku);

        return "redirect:/admin";
    }

    @GetMapping("/admin/update-product/{sku}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addProduct(@PathVariable String sku, Model model) {

        Product product = productService.getProductBySKU(sku);
        model.addAttribute("product", product);
        return "update-product";
    }

    @PostMapping("/admin/update-product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateProduct(@Valid Product product) {

        productService.updateProduct(product);
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

        productService.addProduct(product);

        return "redirect:/admin";
    }

    //////////////////////////////////////////////////////////////

    // get all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{page}/{size}")
    public Page<Product> getPaginatedProducts(@PathVariable int page, @PathVariable int size) {
        return productService.getPaginatedProducts(page, size);
    }

    // get product by sku
    @GetMapping("/products/{sku}")
    public Product getProductBySKU(@PathVariable String sku) {
        return productService.getProductBySKU(sku);
    }

    // add product

    // // delete product by sku
    // @DeleteMapping("/products/{sku}")
    // public void deleteProduct(@PathVariable String sku) {
    // productService.deleteProduct(sku);
    // }

    // update product by sku
    // @PutMapping("/products")
    // public Product updateProduct(@RequestBody Product product) {
    // return productService.updateProduct(product);
    // }

}
