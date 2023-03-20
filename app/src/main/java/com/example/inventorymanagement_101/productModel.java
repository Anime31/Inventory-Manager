package com.example.inventorymanagement_101;

public class productModel {

    private int id;
    private String Name;
    private int quantity;

    //constructors

    public productModel(int id, String name, int quantity) {
        this.id = id;
        Name = name;
        this.quantity = quantity;
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
}
