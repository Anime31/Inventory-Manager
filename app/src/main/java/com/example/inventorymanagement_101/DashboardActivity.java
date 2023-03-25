package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DashboardActivity extends AppCompatActivity {

    ImageButton btn_add, btn_search, btn_newCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn_add = findViewById(R.id.imageButton);
        btn_search = findViewById(R.id.imageButton2);
        btn_newCustomer = findViewById(R.id.imageButton3);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //move to dashboard2
                startActivity(new Intent(DashboardActivity.this, Dashborad2Activity.class));
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //move to searchActivity
                startActivity(new Intent(DashboardActivity.this, SearchActivity.class));
            }
        });

        btn_newCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //move to newCustomerActivity
                startActivity(new Intent(DashboardActivity.this, NewCustomerActivity.class));
            }
        });
    }
}