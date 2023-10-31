package com.example.datn_tranvantruong.Model;

public class ItemCart_Model{
private String idCart;
   private String titleCart;
   private String priceCart;
   private String imageUrl;

    public ItemCart_Model() {
    }

    public ItemCart_Model(String productID, String productName, String productPrice, String productImageUrl) {
this.idCart = productID;
this.titleCart = productName;
this.priceCart = productPrice;
this.imageUrl = productImageUrl;
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public String getTitleCart() {
        return titleCart;
    }

    public void setTitleCart(String titleCart) {
        this.titleCart = titleCart;
    }

    public String getPriceCart() {
        return priceCart;
    }

    public void setPriceCart(String priceCart) {
        this.priceCart = priceCart;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
