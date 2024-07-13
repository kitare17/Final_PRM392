package com.example.myapplication.model;

public class OrderDetail {
    private String productName;
    private int amount;
    private double purchase;
    private String imageUrl;

    public OrderDetail(String productName, int amount, double purchase, String imageUrl) {
        this.productName = productName;
        this.amount = amount;
        this.purchase = purchase;
        this.imageUrl = imageUrl;
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

    @Override
    public String toString() {
        return "OrderDetail{" +
                "productName='" + productName + '\'' +
                ", amount=" + amount +
                ", purchase=" + purchase +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
