package com.example.datn_tranvantruong.Model;

public class BillStatistic {
    private int bill_id;
    private int product_id;
    private int price;
    private String description;
    private String date;

    public BillStatistic() {
    }

    public BillStatistic(int bill_id,int product_id, int price, String description, String date) {
        this.bill_id = bill_id;
        this.product_id = product_id;
        this.price = price;
        this.description = description;
        this.date = date;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
