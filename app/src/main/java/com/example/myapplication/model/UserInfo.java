package com.example.myapplication.model;

public class UserInfo {
    private String userId ;
    private String fullname ;
    private String email  ;

    private String avatar;

    private int role;
    private String googleId ;

    public UserInfo() {
    }

    public UserInfo( String fullname, String email, String avatar, int role, String googleId) {
        this.fullname = fullname;
        this.email = email;
        this.avatar = avatar;
        this.role = role;
        this.googleId = googleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
}
