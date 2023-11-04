package com.example.datn_tranvantruong.Model;

public class User {
    String idUser;
    String name;
    String email;
    String password;

    String phone;

    public User() {
    }

    public User(String idUser, String name, String email, String password, String phone) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
