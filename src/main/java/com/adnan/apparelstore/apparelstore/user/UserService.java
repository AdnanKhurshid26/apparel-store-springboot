package com.adnan.apparelstore.apparelstore.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.adnan.apparelstore.apparelstore.product.Product;
import com.adnan.apparelstore.apparelstore.product.ProductService;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User addUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole("USER");
        user.setAddresses(new ArrayList<>());
        user.setCart(new ArrayList<>());

        return userRepository.save(user);
    }

    public User updateProfile(String username, String name, String address) {
        User user = userRepository.findByEmail(username);
        user.setName(name);
        List<String> addresses = user.getAddresses();
        if (addresses == null) {
            addresses = new ArrayList<String>();
        }
        addresses.add(address);
        user.setAddresses(addresses);
        return userRepository.save(user);
    }

    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    public User updateUser(User updatedUser) {
        User existingUser = userRepository.findByEmail(updatedUser.getEmail());
        if (existingUser != null) {
            updatedUser.setId(existingUser.getId()); // Set the existing ObjectId value
            return userRepository.save(updatedUser);
        } else {
            return null;
        }
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public String addToCart(String email, String sku) {
        User user = userRepository.findByEmail(email);

        List<CartItem> cart = user.getCart();

        Product product = productService.getProductBySKU(sku);

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
            this.updateUser(user);
            return "Added to cart";

        }

        return "Product not found";

    }

    public String removeFromCart(String email, String sku) {
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

    public String updateCart(String email, String sku) {
        User user = userRepository.findByEmail(email);
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

    public List<CartItem> removeNullCartItem(List<CartItem> cart) {
        List<CartItem> newCart = new ArrayList<>();
        for (CartItem cartItem : cart) {
            if (cartItem != null) {
                newCart.add(cartItem);
            }
        }
        return newCart;
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

    public List<CartItem> getUserCart(String email) {
        User user = userRepository.findByEmail(email);
        List<CartItem> cart = user.getCart();
        List<CartItem> newCart = removeNullCartItem(cart);
        return newCart;
    }

    public String check(String email) {

        List<CartItem> cart = getUserCart(email);

        for (CartItem cartItem : cart) {
            Product product = cartItem.getProduct();
            String sku = product.getSKU();
            int quantity = cartItem.getQuantity();
            product.setQuantity(product.getQuantity() - quantity);
            productService.updateProduct(product);
            removeFromCart(email, sku);
        }

        return "Checked out";

    }

}
