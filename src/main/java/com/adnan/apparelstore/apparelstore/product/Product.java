package com.adnan.apparelstore.apparelstore.product;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "products")
public class Product {

    @Id
    @Field("sku")
    private String sku;
    private int quantity;
    private String name;
    private String shortDescription;
    private String description;
    private int price;
    private List<String> categories;
    private List<String> images;
    private List<String> sizes;
    private List<String> colors;



    public Product() {
    }

    public Product(String sku,int qty, String name, String shortDescription, String description, int price, List<String> categories, List<String> images, List<String> sizes, List<String> colors) {
        super();
        this.sku = sku;
        this.name = name;
        this.quantity = qty;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.categories = categories;
        this.images = images;
        this.sizes = sizes;
        this.colors = colors;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product [ sku=" + sku + ", quantity=" + quantity + ", name=" + name
                + ", shortDescription=" + shortDescription + ", description=" + description + ", price=" + price
                + ", categories=" + categories + ", images=" + images + ", sizes=" + sizes + ", colors=" + colors + "]";
    }


    
}
