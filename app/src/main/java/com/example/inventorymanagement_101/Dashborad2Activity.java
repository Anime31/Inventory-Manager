package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

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

    EditText et_productName, et_quantity;
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
                            Integer.parseInt(et_quantity.getText().toString()));

                    Toast.makeText(Dashborad2Activity.this, productModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(Dashborad2Activity.this, "Error Creating product", Toast.LENGTH_SHORT).show();
                    productModel = new productModel(-1, "error", 0);
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(Dashborad2Activity.this);

                boolean success = databaseHelper.addOne(productModel);
                Toast.makeText(Dashborad2Activity.this, "success= " + success, Toast.LENGTH_SHORT).show();

                ShowProductOnListView(databaseHelper);
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(Dashborad2Activity.this);

                ShowProductOnListView(databaseHelper);

//                Toast.makeText(Dashborad2Activity.this, allProduct.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        lv_productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                productModel clickedProduct = (productModel) adapterView.getItemAtPosition(i);
                databaseHelper.deleteOne(clickedProduct);

                ShowProductOnListView(databaseHelper);

                Toast.makeText(Dashborad2Activity.this, "Deleted " + clickedProduct.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowProductOnListView(DatabaseHelper databaseHelper) {
        productArrayAdapter = new ArrayAdapter<productModel> (Dashborad2Activity.this, android.R.layout.simple_list_item_1, databaseHelper.getAll());
        lv_productList.setAdapter(productArrayAdapter);
    }
}