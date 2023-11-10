package com.example.datn_tranvantruong.Model;

public class Cart {
    private int id;
    private String product_name;
    private String id_category;
    private int quality;
    private int price;
    private byte[] image;

    public Cart() {
    }

    public Cart(int id, String product_name, String id_category, int quality, int price, byte[] image) {
        this.id = id;
        this.product_name = product_name;
        this.id_category = id_category;
        this.quality = quality;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
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
