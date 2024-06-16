package com.example.myapplication.model;

public class Brand {
    private int id;
    private String name;
    private int logoResId;

    public Brand(int id, int logoResId, String name) {
        this.id = id;
        this.name = name;
        this.logoResId = logoResId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getLogoResId() { return logoResId; }
}
