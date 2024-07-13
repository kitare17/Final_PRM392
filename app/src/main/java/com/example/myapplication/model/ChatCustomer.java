package com.example.myapplication.model;

public class ChatCustomer {
    String userId;
    String googleId;

    public ChatCustomer(String userId, String googleId) {
        this.userId = userId;
        this.googleId = googleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
}
