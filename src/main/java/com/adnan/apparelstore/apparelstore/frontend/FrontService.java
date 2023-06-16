package com.adnan.apparelstore.apparelstore.frontend;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.adnan.apparelstore.apparelstore.product.Product;
import com.adnan.apparelstore.apparelstore.product.ProductRepository;
import com.adnan.apparelstore.apparelstore.user.CartItem;
import com.adnan.apparelstore.apparelstore.user.User;
import com.adnan.apparelstore.apparelstore.user.UserRepository;
import com.adnan.apparelstore.apparelstore.user.UserService;

@Service
public class FrontService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Product> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products;
    }

    public Page<Product> getPaginatedProducts(int page, int size) {

        Page<Product> products = productRepository.findAll(PageRequest.of(page,
                size));

        return products;
    }

    public Product getProductBySKU(String sku) {

        Product product = productRepository.findBySKU(sku);

        return product;
    }

    public User getUserProfile(Principal principal) {
        String email = principal.getName();

        return userRepository.findByEmail(email);
    }

    public String updateProfile(String username, String name, String address) {
        User user = userRepository.findByEmail(username);
        user.setName(name);
        List<String> addresses = user.getAddresses();
        if (addresses == null) {
            addresses = new ArrayList<String>();
        }
        addresses.add(address);
        user.setAddresses(addresses);

        userRepository.save(user);

        return "Profile updated successfully";
    }

    public String addToCart(String sku, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        List<CartItem> cart = user.getCart();

        Product product = productRepository.findBySKU(sku);

        if (product != null) {

            String productSKU = product.getSKU();
            boolean found = false;

            if (cart == null) {
                cart = new ArrayList<CartItem>();
            }
            for (CartItem cartItem : cart) {
                if (cartItem.getProduct() == null) {
                    cart.remove(cartItem);
                } else if (cartItem.getSku().equals(productSKU)) {
                    if (product.getQuantity() < cartItem.getQuantity() + 1) {
                        return "Not enough stock";
                    }
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                cart.add(cartItem);
            }
            user.setCart(cart);
            userService.updateUser(user);
            return "Added to cart";

        }

        return "Product not found";
    }

    public String removeFromCart(String sku, String email) {
        User user = userRepository.findByEmail(email);
        List<CartItem> cart = user.getCart();

        if (cart == null) {
            return "Cart is empty";
        }
        for (CartItem cartItem : cart) {

            if (cartItem.getSku().equals(sku)) {
                cart.remove(cartItem);
                break;
            }
        }
        user.setCart(cart);
        userRepository.save(user);
        return "Removed from cart";
    }

    public List<CartItem> getCart(String username) {
        User user = userRepository.findByEmail(username);
        return user.getCart();
    }

    public int getCartTotal(List<CartItem> cart) {
        int total = 0;
        for (CartItem cartItem : cart) {
            int price = 0;
            if (cartItem.getProduct() != null)
                price = cartItem.getProduct().getPrice();
            total += cartItem.getQuantity() * price;
        }
        return total;
    }

    public String incCartQty(String sku, Principal principal) {
        addToCart(sku, principal);
        return "Added to cart";
    }

    public String decCartQty(String sku, String username) {
        User user = userRepository.findByEmail(username);
        List<CartItem> cart = user.getCart();

        if (cart == null) {
            return "Cart is empty";
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

        return "Cart updated";
    }

    public String deleteProduct(String sku) {
        productRepository.deleteBySKU(sku);
        return "Product deleted";
    }

    public String updateProduct(Product updatedProduct) {
        Product existingProduct = productRepository.findBySKU(updatedProduct.getSKU());
        if (existingProduct != null) {
            updatedProduct.setId(existingProduct.getId());
            productRepository.save(updatedProduct);
            return "Product updated";
        }
        return "Product not found";
    }

    public String addUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole("USER");
        user.setAddresses(new ArrayList<>());
        user.setCart(new ArrayList<>());

        userRepository.save(user);
        return "User added";
    }

    public List<CartItem> removeNullCartItem(List<CartItem> cart) {
        List<CartItem> newCart = new ArrayList<>();
        for (CartItem cartItem : cart) {
            if (cartItem != null) {
                newCart.add(cartItem);
            }
        }
        return newCart;
    }

    public String checkout(String username) {
        List<CartItem> cartitems = getCart(username);
        List<CartItem> cart = removeNullCartItem(cartitems);

        for (CartItem cartItem : cart) {

            Product product = cartItem.getProduct();
            String sku = product.getSKU();
            int quantity = cartItem.getQuantity();
            product.setQuantity(product.getQuantity() - quantity);
            updateProduct(product);
            removeFromCart(sku, username);

        }

        return "redirect:/";

    }

}
