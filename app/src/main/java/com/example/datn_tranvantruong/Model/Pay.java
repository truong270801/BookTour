package com.example.datn_tranvantruong.Model;

public class Pay {
    int user_id;
    int product_id;
    int quatity;
    int price;
    String description;
    String status;
    String userEmail;
    String userName;
    String userPhone;
    String userAddress;

    public Pay() {
    }

    public Pay(int user_id, int product_id, int quatity, int price, String description,String status, String userEmail, String userName, String userPhone, String userAddress) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.quatity = quatity;
        this.price = price;
        this.description = description;
        this.status = status;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
