package com.example.myapplication.model;


import java.text.NumberFormat;
import java.util.Locale;

public class ProductTest {

    private int id;
    private String name;
    private String type;
    private double price;
    private String imageUrl;  // Changed from int to String

    private String productDetail;

    public ProductTest(int id, String name, String type, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductTest(String imageUrl, String name, int price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }

    public ProductTest() {
    }

    // Getters and setters...

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public String toString() {
        return "ProductTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", productDetail='" + productDetail + '\'' +
                '}';
    }

    public String formatVND(){
        double amount = getPrice();

        // Create a NumberFormat instance for currency formatting in Vietnamese locale
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Format the double value to currency format
        String formattedAmount = currencyFormatter.format(amount);

     return  formattedAmount;
    }
}
