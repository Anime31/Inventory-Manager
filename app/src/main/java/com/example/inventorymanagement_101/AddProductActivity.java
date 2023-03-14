package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    EditText ProductName, Quantity, Price, QR;
    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        myDb = new DatabaseHelper(this);

        ProductName = (EditText) findViewById(R.id.editProductName);
        Quantity = (EditText) findViewById(R.id.editQuantity);
        Price = (EditText) findViewById(R.id.editPrice);
        QR = (EditText) findViewById(R.id.editQR);

        btnAdd = (Button) findViewById(R.id.btnAddItem);

        AddData();
    }

    public void AddData(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //insertdata function returns a boolean valuee

//                boolean isInserted =  myDb.insertData(ProductName.getText().toString(),
//                        Quantity.getText().toString(),
//                        Price.getText().toString(),
//                        QR.getText().toString());
//
//                if(isInserted)
//                    Toast.makeText(AddProductActivity.this,"Error while Inserting",Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(AddProductActivity.this,"Successfully Inserted",Toast.LENGTH_LONG).show();
            }
        });
    }


}