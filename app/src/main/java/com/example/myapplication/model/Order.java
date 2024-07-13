package com.example.myapplication.model;

import java.time.LocalDateTime;

public class Order {

    private int orderId;
    private int userId;
    private String createDate;
    private String address;
    private String phone;
    private String productName;
    private int amount;
    private double purchase;
    private String imageUrl;

    public Order(String productName, String imageUrl, int amount, double purchase, String createDate) {
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.amount = amount;
        this.purchase = purchase;
        this.createDate = createDate;
    }

    public Order(int orderId, int userId, String address, String phone, String createDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.address = address;
        this.phone = phone;
        this.createDate = createDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPurchase() {
        return purchase;
    }

    public void setPurchase(double purchase) {
        this.purchase = purchase;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateDate() {
        LocalDateTime parseDate=LocalDateTime.parse(createDate);

        return  parseDate.getDayOfMonth()+" "+parseDate.getMonth()+" "+parseDate.getYear();
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
