package com.example.datn_tranvantruong.Model;

import java.util.List;

public class Category {
    private int idCategory;
    private String nameCategory;
    private List<Product> products;

    public Category() {
    }

    public Category(int idCategory, String nameCategory, List<Product> products) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this.products = products;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}