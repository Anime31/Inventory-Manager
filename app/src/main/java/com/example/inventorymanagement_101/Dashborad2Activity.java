package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Dashborad2Activity extends AppCompatActivity {

    EditText et_productName, et_quantity, et_price, et_threshold;
    Button btn_viewAll, btn_Add;
    ListView lv_productList;
    ArrayAdapter productArrayAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashborad2);

        et_productName = findViewById(R.id.et_productName);
        et_quantity = findViewById(R.id.et_quantity);
        et_price = findViewById(R.id.et_price);
        et_threshold = findViewById(R.id.et_threshold);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        btn_Add = findViewById(R.id.btn_add);
        lv_productList = findViewById(R.id.lv_productList);

        databaseHelper = new DatabaseHelper(Dashborad2Activity.this);

        ShowProductOnListView(databaseHelper);

        //listners
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productModel productModel;

                //check for invalid entry
                try {
                     productModel = new productModel(-1,et_productName.getText().toString(),
                             Integer.parseInt(et_quantity.getText().toString()),
                             Integer.parseInt(et_price.getText().toString()),
                             Integer.parseInt(et_threshold.getText().toString()));

                    Toast.makeText(Dashborad2Activity.this, productModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(Dashborad2Activity.this, "Error Creating product", Toast.LENGTH_SHORT).show();
                    productModel = new productModel(-1, "error", 0,0,0);
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(Dashborad2Activity.this);


                //if product is present then increment the quantity
                productModel productModel1 = databaseHelper.findProduct(et_productName.getText().toString());

                int previousQuantity = productModel1.getQuantity();
                int increment = productModel.getQuantity();

                //if same product was found then update that
                if(previousQuantity != 0) {
                    databaseHelper.updateProduct(productModel1, previousQuantity+increment);
                    Toast.makeText(Dashborad2Activity.this, "Added " + increment +" item(s) of " + productModel1.getName().toString(), Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean success = databaseHelper.addOne(productModel);

                    Toast.makeText(Dashborad2Activity.this, "success = " + success, Toast.LENGTH_SHORT).show();
                }

                ShowProductOnListView(databaseHelper);
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(Dashborad2Activity.this);

                ShowProductOnListView(databaseHelper);
            }
        });


        //on clicking a product open its details
        lv_productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                productModel clickedProduct = (productModel) adapterView.getItemAtPosition(i);

//                databaseHelper.deleteOne(clickedProduct);
//                ShowProductOnListView(databaseHelper);
//                Toast.makeText(Dashborad2Activity.this, "Deleted " + clickedProduct.toString(), Toast.LENGTH_SHORT).show();

                String str = clickedProduct.getName();
                Intent intent = new Intent(Dashborad2Activity.this, ProductDetailsActivity.class);
                intent.putExtra("key@123", str);

                startActivity(intent);
            }
        });



    }

    private void ShowProductOnListView(DatabaseHelper databaseHelper) {
        productArrayAdapter = new ArrayAdapter<productModel> (Dashborad2Activity.this, android.R.layout.simple_list_item_1, databaseHelper.getAll());
        lv_productList.setAdapter(productArrayAdapter);
    }
}