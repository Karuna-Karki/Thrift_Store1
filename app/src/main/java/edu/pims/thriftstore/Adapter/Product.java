package edu.pims.thriftstore.Adapter;

public class Product {

    private String name;
    private String description;
    private String price;
    private String imageUrl;  // Image URL from Firebase Storage

    // No-argument constructor required for Firestore
    public Product() {}

    public Product(String name, String description, String price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
