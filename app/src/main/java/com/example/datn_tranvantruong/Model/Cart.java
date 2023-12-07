package com.example.datn_tranvantruong.Model;

public class Cart {
    private int id;
    private  int product_id;
    private  int user_id;
    private int quatity;
    private int price;

    public Cart() {
    }

    public Cart(int product_id, int user_id,  int quatity, int price) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.quatity = quatity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quality) {
        this.quatity = quality;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}