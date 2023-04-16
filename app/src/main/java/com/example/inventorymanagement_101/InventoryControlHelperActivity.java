package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

public class InventoryControlHelperActivity extends AppCompatActivity {

    TextView tv_heading;
    ListView lv_inventoryHelper;
    ArrayAdapter productArrayAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_control_helper);

        databaseHelper = new DatabaseHelper(InventoryControlHelperActivity.this);

        tv_heading = findViewById(R.id.tv_heading);
        lv_inventoryHelper = findViewById(R.id.lv_inventoryHelper);

        Intent intent = getIntent();
        String str = intent.getStringExtra("key@1234");

        tv_heading.setText(str);

        if(Objects.equals(str, "Scarce Items")) {

            productArrayAdapter = new ArrayAdapter<productModel>(InventoryControlHelperActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getScarce());
        }
        else if (Objects.equals(str,"Expired Items")) {
            productArrayAdapter = new ArrayAdapter<productModel>(InventoryControlHelperActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getExpired());
        }
        else if (Objects.equals(str,"Perishable Items")){
            productArrayAdapter = new ArrayAdapter<productModel>(InventoryControlHelperActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getPerishable());
        }
//        else if (Objects.equals(str,"Wasted Items")){
//            productArrayAdapter = new ArrayAdapter<productModel>(InventoryControlHelperActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getPerishable());
//        }

        lv_inventoryHelper.setAdapter(productArrayAdapter);

        //on clicking a product open its details
        lv_inventoryHelper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                productModel clickedProduct = (productModel) adapterView.getItemAtPosition(i);

                String str = clickedProduct.getName();
                Intent intent = new Intent(InventoryControlHelperActivity.this, ProductDetailsActivity.class);
                intent.putExtra("key@123", str);

                startActivity(intent);
            }
        });

    }
}