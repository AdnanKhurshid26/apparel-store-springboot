package com.adnan.apparelstore.apparelstore.user;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class User {

    @Id
    @Field("email")
    private String email;
    private String name;
    private String password;
    private List<String> addresses;
    private List<CartItem> cart;

    public User() {
    }

    public User(String email, String name, String password, List<String> addresses, List<CartItem> cart) {
        super();
        this.email = email;
        this.name = name;
        this.password = password;
        this.addresses = addresses;
        this.cart = cart;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", name=" + name + ", password=" + password + ", addresses=" + addresses
                + ", cart=" + cart + "]";
    }
    

   

}
