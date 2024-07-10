package com.example.myapplication.model;


import java.time.LocalDateTime;

public class Rating {
    private String fullname;
    private Double rating;

    private LocalDateTime ratingDate;
    private String detail;
    private int image;
    private String imageUrl;



    public Rating(String fullname, Double rating, LocalDateTime ratingDate, String detail, int image) {
        this.fullname = fullname;
        this.rating = rating;
        this.ratingDate = ratingDate;
        this.detail = detail;
        this.image = image;
    }

    public Rating(String fullname, Double rating, LocalDateTime ratingDate, String detail, String imageUrl) {
        this.fullname = fullname;
        this.rating = rating;
        this.ratingDate = ratingDate;
        this.detail = detail;
        this.imageUrl = imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDateTime getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(LocalDateTime ratingDate) {
        this.ratingDate = ratingDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "fullname='" + fullname + '\'' +
                ", rating=" + rating +
                ", ratingDate=" + ratingDate +
                ", detail='" + detail + '\'' +
                ", image=" + imageUrl +
                '}';
    }
}
