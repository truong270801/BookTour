package com.example.datn_tranvantruong.Model;

public class Bill {
    private int id;
    private  int user_id;
    private int product_id;
    private  int quatity;
    private int price;
    private  String description;
    private  String date_created;

    public Bill() {
    }

    public Bill(int user_id, int product_id,int quatity, int price, String description, String date_created) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.quatity = quatity;
        this.price = price;
        this.description = description;
        this.date_created = date_created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}