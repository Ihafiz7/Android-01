package com.hafiz.android_01.entity;

public class Product {
    private  int id;
    private String name;
    private String email;
    private Double price;
    private Integer quantity;
    private String image;

    public Product() { }

    public Product(int id, String name, String email, Double price, Integer quantity, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public Product(String name, String email, Double price, Integer quantity,String image) {
        this.name = name;
        this.email = email;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
