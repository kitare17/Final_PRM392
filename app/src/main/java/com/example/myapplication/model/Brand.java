package com.example.myapplication.model;

public class Brand {
    private int id;
    private String logoResId; // Drawable resource ID
    private String name;

    public Brand(int id, String logoResId, String name) {
        this.id = id;
        this.logoResId = logoResId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getLogoResId() {
        return logoResId;
    }

    public void setLogoResId(String logoResId) {
        this.logoResId = logoResId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", logoResId='" + logoResId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}
