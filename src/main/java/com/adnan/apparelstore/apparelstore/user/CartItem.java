package com.adnan.apparelstore.apparelstore.user;

public class CartItem {

    private String sku;
    private String name;
    private String imageUrl;
    private int price;
    private int quantity;
    
    public CartItem() {
    }

    public CartItem(String sku, String name, String imageUrl, int price, int quantity) {
        super();
        this.sku = sku;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem [sku=" + sku + ", name=" + name + ", imageUrl=" + imageUrl + ", price=" + price + ", quantity="
                + quantity + "]";
    }

    

    
    
}
