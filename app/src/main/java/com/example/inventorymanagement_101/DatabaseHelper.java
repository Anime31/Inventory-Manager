package com.example.inventorymanagement_101;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String PRODUCT_TABLE = "PRODUCT_TABLE";
    public static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String COLUMN_PRODUCT_QUANTITY = "PRODUCT_QUANTITY";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PRICE = "PRICE";
    public static final String COLUMN_THRESHOLD = "THRESHOLD";
//    public static final String COLUMN_EXPIRY_DATE = "EXPIRY_DATE";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "product1.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + PRODUCT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_NAME + " TEXT, " + COLUMN_PRODUCT_QUANTITY + " INT, " + COLUMN_PRICE + " INT )";
//        + COLUMN_THRESHOLD + " INT) ";

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
//        cv.put(COLUMN_THRESHOLD, productModel.getThreshold());

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
//                int productThreshold = cursor.getInt(4);


                if(Objects.equals(productName, s)) {
//                    System.out.println("found");

                    return new productModel(productID,productName,productQuantity,productPrice);
                }

            }while (cursor.moveToNext());

        }
        else {
            //do nothing
        }

        cursor.close();
        db.close();

        return new productModel(-1,"N/A",0,0);
    }

    public void updateProduct(String s, int newQuantity) {

    }

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
//                int productThreshold = cursor.getInt(4);

                productModel newProduct = new productModel(productID,productName,productQuantity,productPrice);

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



}
