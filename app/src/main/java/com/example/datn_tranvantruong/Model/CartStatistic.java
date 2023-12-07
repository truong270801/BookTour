package com.example.datn_tranvantruong.Model;

public class CartStatistic {
    private int idCart;
    private int product_id;
    private String product_name;
    private int quatity;
    private int total;
    private byte[] product_image;

    public CartStatistic() {
    }

    public CartStatistic(int idCart, int product_id, String product_name, int quatity, int total, byte[] product_image) {
        this.idCart = idCart;
        this.product_id = product_id;
        this.product_name = product_name;
        this.quatity = quatity;
        this.total = total;
        this.product_image = product_image;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public byte[] getProduct_image() {
        return product_image;
    }

    public void setProduct_image(byte[] product_image) {
        this.product_image = product_image;
    }
}