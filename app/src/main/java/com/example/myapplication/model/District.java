package com.example.myapplication.model;

public class District {
    String idProvince;
    String idDistrict;
    String name;

    public District() {
    }

    public District(String idProvince, String idDistrict, String name) {
        this.idProvince = idProvince;
        this.idDistrict = idDistrict;
        this.name = name;
    }

    public String getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(String idProvince) {
        this.idProvince = idProvince;
    }

    public String getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(String idDistrict) {
        this.idDistrict = idDistrict;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "District{" +
                "idProvince='" + idProvince + '\'' +
                ", idDistrict='" + idDistrict + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
