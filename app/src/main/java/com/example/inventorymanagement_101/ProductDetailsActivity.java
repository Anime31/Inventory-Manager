package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView tv_productName, tv_showId, tv_showQuantity, tv_showPrice, tv_showExpiryDate, tv_showAddedDate, tv_showThreshold;
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



        btn_deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseHelper.deleteOne(product);
                Toast.makeText(ProductDetailsActivity.this, "Deleted " + product.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}