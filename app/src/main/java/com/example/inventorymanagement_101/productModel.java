package com.example.inventorymanagement_101;

import java.util.Date;

public class productModel {

    private int id;
    private String Name;
    private int quantity;
    private int price;
    private int threshold;
    private int addedDate;
    private int expiryDate;
    private int wastage;


    //constructors
    public productModel(int id, String name, int quantity, int price, int threshold, int addedDate, int expiryDate, int wastage) {
        this.id = id;
        Name = name;
        this.quantity = quantity;
        this.price = price;
        this.threshold = threshold;
        this.addedDate = addedDate;
        this.expiryDate = expiryDate;
        this.wastage = wastage;
    }

    public productModel() {
    }


    @Override
    public String toString() {
        return "id=" + id +
                ", Name='" + Name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", threshold=" + threshold;
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

    public int getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(int addedDate) {
        this.addedDate = addedDate;
    }

    public int getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(int expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getWastage() {
        return wastage;
    }

    public void setWastage(int wastage) {
        this.wastage = wastage;
    }
}
