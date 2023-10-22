package com.example.datn_tranvantruong.Model;

public class ItemHome_Model {
     int id;
     String title;
     String location;
     String price;
     static byte[] imgtour;

     public ItemHome_Model(int id, String title, String location, String price, byte[] imgtour) {
          this.id = id;
          this.title = title;
          this.location = location;
          this.price = price;
          this.imgtour = imgtour;
     }



     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

     public String getLocation() {
          return location;
     }

     public void setLocation(String location) {
          this.location = location;
     }

     public String getPrice() {
          return price;
     }

     public void setPrice(String price) {
          this.price = price;
     }
     public static byte[] getImgtour() {
          return imgtour;
     }

     public void setImgtour(byte[] imgtour) {
          this.imgtour = imgtour;
     }
}
