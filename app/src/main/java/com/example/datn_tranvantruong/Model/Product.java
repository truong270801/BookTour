package com.example.datn_tranvantruong.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String categoryName;
    private String name;
    private String startdate;
    private String enddate;
    private  String description;
    private  String location;
    private int price;
    private byte[] image;

    public Product() {
    }

    public Product(int id, String categoryName, String name, String startdate, String enddate, String description, String location, int price, byte[] image) {
        this.id = id;
        this.categoryName = categoryName;
        this.name = name;
        this.startdate = startdate;
        this.enddate = enddate;
        this.description = description;
        this.location = location;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
