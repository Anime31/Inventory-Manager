package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView tv_productName, tv_showId, tv_showQuantity, tv_showPrice, tv_showExpiryDate, tv_showAddedDate, tv_showThreshold, tv_showWastage;
    EditText et_damaged;
    Button btn_removeDamaged, btn_deleteProduct;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tv_productName = findViewById(R.id.tv_productName);
        tv_showId = findViewById(R.id.tv_showId);
        tv_showQuantity = findViewById(R.id.tv_showQuantity);
        tv_showPrice = findViewById(R.id.tv_showPrice);
        tv_showExpiryDate = findViewById(R.id.tv_showExpiryDate);
        tv_showAddedDate = findViewById(R.id.tv_showAddedDate);
        tv_showThreshold = findViewById(R.id.tv_showThreshold);
        tv_showWastage = findViewById(R.id.tv_showWastage);

        et_damaged = findViewById(R.id.et_damaged);
        btn_removeDamaged = findViewById(R.id.btn_removeDamaged);
        btn_deleteProduct = findViewById(R.id.btn_deleteProduct);


        Intent intent = getIntent();
        String str = intent.getStringExtra("key@123");

        tv_productName.setText(str);

        databaseHelper = new DatabaseHelper(ProductDetailsActivity.this);
        productModel product = databaseHelper.findProduct(str);

        tv_showId.setText(String.valueOf(product.getId()));
        tv_showQuantity.setText(String.valueOf(product.getQuantity()));
        tv_showPrice.setText(String.valueOf(product.getPrice()));
        tv_showThreshold.setText(String.valueOf(product.getThreshold()));
        tv_showAddedDate.setText(convertDate(product.getAddedDate()));
        tv_showExpiryDate.setText(convertDate(product.getExpiryDate()));
        tv_showWastage.setText(String.valueOf(product.getWastage()));

        if(product.getQuantity() < product.getThreshold()) {
            tv_showQuantity.setTextColor(Color.RED);
            tv_showQuantity.setError("Low Quantity");
        }
        if(currentDate() >= product.getExpiryDate()) {
            tv_showExpiryDate.setTextColor(Color.RED);
            tv_showExpiryDate.setError("Item Expired!");
        }


        btn_removeDamaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                databaseHelper = new DatabaseHelper(ProductDetailsActivity.this);

                int damagedQuantity = Integer.parseInt(et_damaged.getText().toString());
                int currentQuantity = product.getQuantity();

                if(currentQuantity >= damagedQuantity) {
                    //update the quantity

                    int newQuantity = currentQuantity - damagedQuantity;

                    databaseHelper.updateProduct(product,newQuantity);

                    if(newQuantity==0) {
                        databaseHelper.deleteOne(product);
                    }

                    //increment in wastage of this product
                    databaseHelper.addWastage(product, damagedQuantity);

                    Toast.makeText(ProductDetailsActivity.this, "Successfully Removed " + damagedQuantity + " item(s)", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProductDetailsActivity.this,Dashborad2Activity.class));

                }
                else {
                    Toast.makeText(ProductDetailsActivity.this, "Not Sufficient Quantity Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseHelper.deleteOne(product);
                Toast.makeText(ProductDetailsActivity.this, "Deleted " + product.getName(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(ProductDetailsActivity.this,Dashborad2Activity.class));
            }
        });

    }

    private String convertDate(int x) {
//        yyyymmdd

        String str = "";
        String date = String.valueOf(x);

        str += date.substring(6);
        str += '-';
        str += date.substring(4,6);
        str += '-';
        str += date.substring(0,4);

        return str;

    }

    int currentDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return year*10000 + month*100 + day;
    }
}