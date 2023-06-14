package com.adnan.apparelstore.apparelstore.frontend;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adnan.apparelstore.apparelstore.product.Product;
import com.adnan.apparelstore.apparelstore.product.ProductRepository;
import com.adnan.apparelstore.apparelstore.user.CartItem;
import com.adnan.apparelstore.apparelstore.user.User;

@Service
public class FrontService {
    
    @Autowired
    private  ProductRepository productRepository;

    private RestTemplate restTemplate = new RestTemplate();
    private String sourceurl = "http://localhost:8080";
    private HttpMethod getMethod = HttpMethod.GET;
    private HttpMethod postMethod = HttpMethod.POST;
    private HttpMethod putMethod = HttpMethod.PUT;
    private HttpMethod deleteMethod = HttpMethod.DELETE;

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }
    
    
    public List<Product> getAllProducts() {
        String url = sourceurl + "/products";
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<List<Product>> responseType = new ParameterizedTypeReference<List<Product>>() {};
        ResponseEntity<List<Product>> response = exchange(url, getMethod, requestEntity, responseType);
        List<Product> products = response.getBody();

        return products;
    }

    public Page<Product> getPaginatedProducts(int page, int size) {
        
        Page<Product> products = productRepository.findAll(PageRequest.of(page, size));

        return products;
    }

    public Product getProductBySKU(String sku) {
        String url = sourceurl + "/products/"+sku;
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<Product> responseType = new ParameterizedTypeReference<Product>() {};
        ResponseEntity<Product> response = exchange(url, getMethod, requestEntity, responseType);
        Product product = response.getBody();

        return product;
    }

    public User getUserProfile() {
        String url = sourceurl + "/users/adnan@gmail.com";
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<User> responseType = new ParameterizedTypeReference<User>() {};
        ResponseEntity<User> response = exchange(url, getMethod, requestEntity, responseType);
        User user = response.getBody();

        return user;
    }

    public String updateProfile(User user) {
        String url = sourceurl + "/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> requestEntity = new HttpEntity<User>(user, headers);
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> response = exchange(url, postMethod, requestEntity, responseType);
        String message = response.getBody();

        return message;
    }

    public String addToCart(String sku) {
        String url = sourceurl + "/users/adnan@gmail.com/cart/"+sku;
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> response = exchange(url, postMethod, requestEntity, responseType);
        String message = response.getBody();

        return message;
    }

    public String removeFromCart(String sku) {
        String url = sourceurl + "/users/adnan@gmail.com/cart/"+sku;
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> response = exchange(url, deleteMethod, requestEntity, responseType);
        String message = response.getBody();

        return message;
    }

    public List<CartItem> getCart() {
        String url = sourceurl + "/users/adnan@gmail.com/cart";
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<List<CartItem>> responseType = new ParameterizedTypeReference<List<CartItem>>() {};
        ResponseEntity<List<CartItem>> response = exchange(url, getMethod, requestEntity, responseType);
        List<CartItem> cart = response.getBody();

       
        return cart;
    }

    public int getCartTotal(List<CartItem> cart) {
        int total = 0;
        for (CartItem cartItem : cart) {
            int price=0; 
            if(cartItem.getProduct()!=null) price = cartItem.getProduct().getPrice();
            total += cartItem.getQuantity() * price ;
        }
        return total;
    }

    public String incCartQty(String sku) {
        String url = sourceurl + "/users/adnan@gmail.com/cart/"+sku;
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> response = exchange(url, postMethod, requestEntity, responseType);
        String message = response.getBody();

        return message;
    }

    public String decCartQty(String sku) {
        String url = sourceurl + "/users/adnan@gmail.com/cart/"+sku;
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> response = exchange(url, putMethod, requestEntity, responseType);
        String message = response.getBody();

        return message;
    }

    public String deleteProduct(String sku) {
        String url = sourceurl + "/products/"+sku;
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> response = exchange(url, deleteMethod, requestEntity, responseType);
        String message = response.getBody();

        return message;
    }

    public String updateProduct(Product product) {
        String url = sourceurl + "/products";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); 
        HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> response = exchange(url, putMethod, requestEntity, responseType);
        String message = response.getBody();

        return message;
    }

    public String addUser(User user) {
        String url = sourceurl + "/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); 
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> response = exchange(url, postMethod, requestEntity, responseType);
        String message = response.getBody();

        return message;
    }

    public List<CartItem> removeNullCartItem(List<CartItem> cart) {
        List<CartItem> newCart = new ArrayList<>();
        for (CartItem cartItem : cart) {
            if(cartItem != null) {
                newCart.add(cartItem);
            }
        }
        return newCart;
    }

   

}
