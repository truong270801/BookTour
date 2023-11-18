package com.example.datn_tranvantruong.Model;

public class BillRevenue {
    private int bill_id;
    private int quatity;
    private String price;

    public BillRevenue() {
    }

    public BillRevenue(int bill_id, int quatity, String price) {
        this.bill_id = bill_id;
        this.quatity = quatity;
        this.price = price;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String date) {
        this.price = date;
    }
}
