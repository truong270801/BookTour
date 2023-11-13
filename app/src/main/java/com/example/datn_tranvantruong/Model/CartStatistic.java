package com.example.datn_tranvantruong.Model;

public class CartStatistic {
private int idCart;
    private String product_name;
    private int quatity;
    private float total;
    private byte[] product_image;

    public CartStatistic() {
    }

    public CartStatistic(int idCart, String product_name, int quatity, float total, byte[] product_image) {
        this.idCart = idCart;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public byte[] getProduct_image() {
        return product_image;
    }

    public void setProduct_image(byte[] product_image) {
        this.product_image = product_image;
    }
}
