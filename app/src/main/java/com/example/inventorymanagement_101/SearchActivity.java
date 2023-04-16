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
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    SearchView sv_product;
    Button btn_search;
    ListView lv_searchProduct;
    DatabaseHelper databaseHelper;
    ArrayAdapter productArrayAdapter;
    List<productModel> allProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sv_product = findViewById(R.id.sv_product);
        btn_search = findViewById(R.id.btn_search);
        lv_searchProduct = findViewById(R.id.lv_searchProduct);

        databaseHelper = new DatabaseHelper(SearchActivity.this);
        allProduct = databaseHelper.getAll();

        productArrayAdapter = new ArrayAdapter<productModel>(SearchActivity.this, android.R.layout.simple_list_item_1, allProduct);
        lv_searchProduct.setAdapter(productArrayAdapter);

        sv_product.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                List<productModel> filteredProducts = new ArrayList<>();

                for(productModel product:allProduct) {

                    // checking product name
                    if(product.getName().toLowerCase().contains(s.toLowerCase())) {
                        filteredProducts.add(product);
                    }

                    // checking batch number
                    else if(String.valueOf(product.getBatchNumber()).contains(s)) {
                        filteredProducts.add(product);
                    }
                }

                productArrayAdapter = new ArrayAdapter<productModel>(SearchActivity.this, android.R.layout.simple_list_item_1, filteredProducts);
                lv_searchProduct.setAdapter(productArrayAdapter);


                return false;
            }
        });

        lv_searchProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                productModel clickedProduct = (productModel) adapterView.getItemAtPosition(i);

                String str = clickedProduct.getName();
                Intent intent = new Intent(SearchActivity.this, ProductDetailsActivity.class);
                intent.putExtra("key@123", str);

                startActivity(intent);
            }
        });

    }

}