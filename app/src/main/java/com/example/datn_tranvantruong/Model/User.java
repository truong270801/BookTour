package com.example.datn_tranvantruong.Model;

public class User {
    int id;
    String fullname;
    String email;
    String address;
    String password;
    String phone;
    private byte[] image_avatar;

    public User() {
    }

    public User(int id, String fullname, String email, String address, String password, String phone, byte[] image_avatar) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.phone = phone;
        this.image_avatar = image_avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String full_name) {
        this.fullname = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getImage_avatar() {
        return image_avatar;
    }

    public void setImage_avatar(byte[] image_avatar) {
        this.image_avatar = image_avatar;
    }
}
