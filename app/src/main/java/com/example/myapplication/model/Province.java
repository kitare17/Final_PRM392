package com.example.myapplication.model;

public class Province {

    String idProvince;
    String name;

    public Province() {
    }



    public Province(String idProvince, String name) {
        this.idProvince = idProvince;
        this.name = name;
    }

    public String getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(String idProvince) {
        this.idProvince = idProvince;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
