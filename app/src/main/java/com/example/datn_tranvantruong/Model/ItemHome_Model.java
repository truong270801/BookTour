package com.example.datn_tranvantruong.Model;

public class ItemHome_Model {
    private String imageUrl;
    private String name;
    private String location;
    private String price;

    public ItemHome_Model() {
    }

    public ItemHome_Model(String imageUrl, String name, String location, String price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.location = location;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
