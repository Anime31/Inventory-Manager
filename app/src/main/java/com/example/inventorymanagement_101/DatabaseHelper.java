package com.example.inventorymanagement_101;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String PRODUCT_TABLE = "PRODUCT_TABLE";
    public static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String COLUMN_PRODUCT_QUANTITY = "PRODUCT_QUANTITY";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PRICE = "PRICE";
    public static final String COLUMN_THRESHOLD = "THRESHOLD";
    public static final String COLUMN_EXPIRY_DATE = "EXPIRY_DATE";
    public static final String COLUMN_ADDED_DATE = "ADDED_DATE";
    public static final String COLUMN_WASTAGE = "WASTAGE";
    public static final String COLUMN_BATCH_NUMBER = "BATCH_NUMBER";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "product6.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + PRODUCT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_NAME + " TEXT, " + COLUMN_PRODUCT_QUANTITY + " INT, " + COLUMN_PRICE + " INT, " + COLUMN_THRESHOLD + " INT, "
                + COLUMN_ADDED_DATE + " INT, " + COLUMN_EXPIRY_DATE + " INT, " + COLUMN_WASTAGE + " INT, " + COLUMN_BATCH_NUMBER + " INT )";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean addOne(productModel productModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PRODUCT_NAME, productModel.getName());
        cv.put(COLUMN_PRODUCT_QUANTITY, productModel.getQuantity());
        cv.put(COLUMN_PRICE, productModel.getPrice());
        cv.put(COLUMN_THRESHOLD, productModel.getThreshold());
        cv.put(COLUMN_ADDED_DATE, productModel.getAddedDate());
        cv.put(COLUMN_EXPIRY_DATE, productModel.getExpiryDate());
        cv.put(COLUMN_BATCH_NUMBER, productModel.getBatchNumber());

        long insert = db.insert(PRODUCT_TABLE, null, cv);

        if(insert == -1)
            return false;
        else
            return true;

    }

    public boolean deleteOne(productModel productModel) {
        //if productModel found in database, then delete and return true
        //else return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + PRODUCT_TABLE + " WHERE " + COLUMN_ID + "= " + productModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }

    //return the product if found else return product with 0 quantity
    public productModel findProduct(String s) {

        //getting data from database
        String queryString = "SELECT * FROM " + PRODUCT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new product objects and put them into returnList

            do {

                int productID = cursor.getInt(0);
                String productName = cursor.getString(1);
                int productQuantity = cursor.getInt(2);
                int productPrice = cursor.getInt(3);
                int productThreshold = cursor.getInt(4);
                int productAdded = cursor.getInt(5);
                int productExpiry = cursor.getInt(6);
                int productWastage = cursor.getInt(7);
                int productBatch = cursor.getInt(8);


                if(Objects.equals(productName, s)) {
//                    System.out.println("found");

                    return new productModel(productID,productName,productQuantity,productPrice,productThreshold,productAdded,productExpiry,productWastage,productBatch);
                }

            }while (cursor.moveToNext());

        }
        else {
            //do nothing
        }

        cursor.close();
        db.close();

        return new productModel(-1,"N/A",0,0,0,currentDate(),currentDate(),0,0);
    }

    //update the quantity of product with PRODUCT_NAME = productName
    public void updateProduct(productModel product, int newQuantity) {

        int productID = product.getId();
        String productName = product.getName();
        int productQuantity = product.getQuantity();
        int productPrice = product.getPrice();
        int productThreshold = product.getThreshold();
        int productAdded = product.getAddedDate();
        int productExpiry = product.getExpiryDate();
        int productWastage = product.getWastage();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PRODUCT_NAME, productName);
        cv.put(COLUMN_PRODUCT_QUANTITY, newQuantity);
//        cv.put(COLUMN_PRICE, productPrice);
//        cv.put(COLUMN_THRESHOLD, productThreshold);
//        cv.put(COLUMN_ADDED_DATE, productAdded);
//        cv.put(COLUMN_EXPIRY_DATE, productExpiry);
//        cv.put(COLUMN_WASTAGE, productWastage);


        db.update(PRODUCT_TABLE, cv, "PRODUCT_NAME=?", new String[]{productName});
        db.close();

    }

    //return all product
    public List<productModel> getAll() {

        List<productModel> returnList = new ArrayList<>();

        //getting data from database
        String queryString = "SELECT * FROM " + PRODUCT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new product objects and put them into returnList

            do {

                int productID = cursor.getInt(0);
                String productName = cursor.getString(1);
                int productQuantity = cursor.getInt(2);
                int productPrice = cursor.getInt(3);
                int productThreshold = cursor.getInt(4);
                int productAdded = cursor.getInt(5);
                int productExpiry = cursor.getInt(6);
                int productWastage = cursor.getInt(7);
                int productBatch = cursor.getInt(8);

                productModel newProduct = new productModel(productID,productName,productQuantity,productPrice,productThreshold,productAdded,productExpiry,productWastage,productBatch);

                returnList.add(newProduct);

            }while (cursor.moveToNext());

        }

        else {
            //do not add anything to the list
        }

        cursor.close();
        db.close();

        return returnList;
    }

    //returns list of scarce products
    public List<productModel> getScarce() {
        List<productModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM PRODUCT_TABLE WHERE PRODUCT_QUANTITY < THRESHOLD";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new product objects and put them into returnList

            do {

                int productID = cursor.getInt(0);
                String productName = cursor.getString(1);
                int productQuantity = cursor.getInt(2);
                int productPrice = cursor.getInt(3);
                int productThreshold = cursor.getInt(4);
                int productAdded = cursor.getInt(5);
                int productExpiry = cursor.getInt(6);
                int productWastage = cursor.getInt(7);
                int productBatch = cursor.getInt(8);

                productModel newProduct = new productModel(productID,productName,productQuantity,productPrice,productThreshold,productAdded,productExpiry,productWastage,productBatch);

                returnList.add(newProduct);

            }while (cursor.moveToNext());

        }

        else {
            //do not add anything to the list
        }

        cursor.close();
        db.close();

        return returnList;
    }

    //returns list of expired products
    public List<productModel> getExpired() {
        List<productModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM PRODUCT_TABLE WHERE EXPIRY_DATE <= " + currentDate();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new product objects and put them into returnList

            do {

                int productID = cursor.getInt(0);
                String productName = cursor.getString(1);
                int productQuantity = cursor.getInt(2);
                int productPrice = cursor.getInt(3);
                int productThreshold = cursor.getInt(4);
                int productAdded = cursor.getInt(5);
                int productExpiry = cursor.getInt(6);
                int productWastage = cursor.getInt(7);
                int productBatch = cursor.getInt(8);

                productModel newProduct = new productModel(productID,productName,productQuantity,productPrice,productThreshold,productAdded,productExpiry,productWastage,productBatch);

                returnList.add(newProduct);

            }while (cursor.moveToNext());

        }

        else {
            //do not add anything to the list
        }

        cursor.close();
        db.close();

        return returnList;
    }

    int currentDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return year*10000 + month*100 + day;
    }

    //returns list of perishable products
    public List<productModel> getPerishable() {
        List<productModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM PRODUCT_TABLE WHERE EXPIRY_DATE <= " + (currentDate()+15);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new product objects and put them into returnList

            do {

                int productID = cursor.getInt(0);
                String productName = cursor.getString(1);
                int productQuantity = cursor.getInt(2);
                int productPrice = cursor.getInt(3);
                int productThreshold = cursor.getInt(4);
                int productAdded = cursor.getInt(5);
                int productExpiry = cursor.getInt(6);
                int productWastage = cursor.getInt(7);
                int productBatch = cursor.getInt(8);

                productModel newProduct = new productModel(productID,productName,productQuantity,productPrice,productThreshold,productAdded,productExpiry,productWastage,productBatch);

                returnList.add(newProduct);

            }while (cursor.moveToNext());

        }

        else {
            //do not add anything to the list
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public List<productModel> getWasted() {
        List<productModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM PRODUCT_TABLE WHERE WASTAGE IS NOT NULL";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new product objects and put them into returnList

            do {

                int productID = cursor.getInt(0);
                String productName = cursor.getString(1);
                int productQuantity = cursor.getInt(2);
                int productPrice = cursor.getInt(3);
                int productThreshold = cursor.getInt(4);
                int productAdded = cursor.getInt(5);
                int productExpiry = cursor.getInt(6);
                int productWastage = cursor.getInt(7);
                int productBatch = cursor.getInt(8);

                productModel newProduct = new productModel(productID,productName,productQuantity,productPrice,productThreshold,productAdded,productExpiry,productWastage,productBatch);

                returnList.add(newProduct);

            }while (cursor.moveToNext());

        }

        else {
            //do not add anything to the list
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public void addWastage(productModel product, int wastage) {

        int productID = product.getId();
        String productName = product.getName();
        int productQuantity = product.getQuantity();
        int productPrice = product.getPrice();
        int productThreshold = product.getThreshold();
        int productAdded = product.getAddedDate();
        int productExpiry = product.getExpiryDate();
        int productWastage = product.getWastage();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PRODUCT_NAME, productName);
//        cv.put(COLUMN_PRODUCT_QUANTITY, productQuantity);
//        cv.put(COLUMN_PRICE, productPrice);
//        cv.put(COLUMN_THRESHOLD, productThreshold);
//        cv.put(COLUMN_ADDED_DATE, productAdded);
//        cv.put(COLUMN_EXPIRY_DATE, productExpiry);
        cv.put(COLUMN_WASTAGE, productWastage + wastage);

        db.update(PRODUCT_TABLE, cv, "PRODUCT_NAME=?", new String[]{productName});
        db.close();

    }


    public List<productModel> getFilteredProductSearch(int minQuantity, int maxQuantity, int minPrice, int maxPrice, int minExpiry, int maxExpiry) {
        List<productModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM PRODUCT_TABLE WHERE PRODUCT_QUANTITY >= " + minQuantity +
                " AND PRODUCT_QUANTITY <= " + maxQuantity +
                " AND PRICE >= " + minPrice +
                " AND PRICE <= " + maxPrice +
                " AND EXPIRY_DATE >= " + minExpiry +
                " AND EXPIRY_DATE <= " + maxExpiry;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new product objects and put them into returnList

            do {

                int productID = cursor.getInt(0);
                String productName = cursor.getString(1);
                int productQuantity = cursor.getInt(2);
                int productPrice = cursor.getInt(3);
                int productThreshold = cursor.getInt(4);
                int productAdded = cursor.getInt(5);
                int productExpiry = cursor.getInt(6);
                int productWastage = cursor.getInt(7);
                int productBatch = cursor.getInt(8);

                productModel newProduct = new productModel(productID,productName,productQuantity,productPrice,productThreshold,productAdded,productExpiry,productWastage,productBatch);

                returnList.add(newProduct);

            }while (cursor.moveToNext());

        }

        else {
            //do not add anything to the list
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public List<Pair<Integer, Integer>> plotQuantity() {
        List<Pair<Integer, Integer>> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM PRODUCT_TABLE";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new product objects and put them into returnList

            do {

                int productID = cursor.getInt(0);
                int productQuantity = cursor.getInt(2);

                returnList.add(new Pair<Integer, Integer>(productID, productQuantity));

            }while (cursor.moveToNext());

        }

        else {
            //do not add anything to the list
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public List<Pair<Integer, Integer>> plotPrice() {
        List<Pair<Integer, Integer>> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM PRODUCT_TABLE";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new product objects and put them into returnList

            do {

                int productID = cursor.getInt(0);
                int productPrice = cursor.getInt(3);

                returnList.add(new Pair<Integer, Integer>(productID, productPrice));

            }while (cursor.moveToNext());

        }

        else {
            //do not add anything to the list
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public List<Pair<Integer, Integer>> plotWastage() {
        List<Pair<Integer, Integer>> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM PRODUCT_TABLE";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new product objects and put them into returnList

            do {

                int productID = cursor.getInt(0);
                int productWastage = cursor.getInt(7);

                returnList.add(new Pair<Integer, Integer>(productID, productWastage));

            }while (cursor.moveToNext());

        }

        else {
            //do not add anything to the list
        }

        cursor.close();
        db.close();

        return returnList;
    }
}
