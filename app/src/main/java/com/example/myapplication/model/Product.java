package com.example.myapplication.model;

public class Product {

    private int id;
    private String name;
    private String type;
    private double price;
    private int  imageUrl;

    public Product(int id, String name, String type, double price, int imageUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.imageUrl = imageUrl;
    }
    public Product() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int  getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int  imageUrl) {
        this.imageUrl = imageUrl;
    }
}