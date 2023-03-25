package com.example.inventorymanagement_101;

import java.util.Date;

public class productModel {

    private int id;
    private String Name;
    private int quantity;
    private int price;
    private int threshold;
//    private Date expiryDate;


    //constructors
    public productModel(int id, String name, int quantity) {
        this.id = id;
        Name = name;
        this.quantity = quantity;
//        this.price = price;
//        this.threshold = threshold;
    }

    public productModel() {
    }


    //toString is neccesary to print content of a class object
    @Override
    public String toString() {
        return "id=" + id +
                ", Name='" + Name + '\'' +
                ", quantity=" + quantity ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
