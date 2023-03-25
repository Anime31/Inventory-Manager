package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewCustomerActivity extends AppCompatActivity {

    EditText et_customerName, et_phone, et_email, et_customerProduct, et_customerQuantity;
    Button btn_addToBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        et_customerName = findViewById(R.id.et_customerName);
        et_phone = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);
        et_customerProduct = findViewById(R.id.et_customerProduct);
        et_customerQuantity = findViewById(R.id.et_customerQuantity);
        btn_addToBill = findViewById(R.id.btn_addToBill);

        btn_addToBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper databaseHelper = new DatabaseHelper(NewCustomerActivity.this);
                productModel product = databaseHelper.findProduct(et_customerProduct.getText().toString());

                if(product.getQuantity()==0) {
                    Toast.makeText(NewCustomerActivity.this, "Product Not Available", Toast.LENGTH_SHORT).show();
                }
                else {
                    int requiredQuantity = Integer.parseInt(et_customerQuantity.getText().toString());
                    int currentQuantity = product.getQuantity();

                    if(currentQuantity >= requiredQuantity) {
                        int newQuantity = currentQuantity - requiredQuantity;
                        product.setQuantity(newQuantity);

                        //delete product
                        databaseHelper.deleteOne(product);

                        //add product with new quantity(>0)
                        if(newQuantity > 0) {
                            databaseHelper.addOne(product);
                        }

                        Toast.makeText(NewCustomerActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(NewCustomerActivity.this, "Not Sufficient Quantity Available", Toast.LENGTH_SHORT).show();
                    }
                }

                et_customerProduct.setText("");
                et_customerQuantity.setText("");
            }
        });

    }
}