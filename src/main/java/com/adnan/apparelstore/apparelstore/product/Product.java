package com.adnan.apparelstore.apparelstore.product;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @Indexed(unique = true)
    private String SKU;
    private int quantity;
    private String name;
    private String shortDescription;
    private String description;
    private int price;
    private List<String> categories;
    private List<String> images;
    private List<String> size;
    private List<String> color;



    public Product() {
    }

    public Product(String SKU,int qty, String name, String shortDescription, String description, int price, List<String> categories, List<String> images, List<String> size, List<String> color) {
        super();
        this.SKU = SKU;
        this.name = name;
        this.quantity = qty;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.categories = categories;
        this.images = images;
        this.size = size;
        this.color = color;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
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

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product [ SKU=" + SKU + ", quantity=" + quantity + ", name=" + name
                + ", shortDescription=" + shortDescription + ", description=" + description + ", price=" + price
                + ", categories=" + categories + ", images=" + images + ", size=" + size + ", color=" + color + "]";
    }


    
}
