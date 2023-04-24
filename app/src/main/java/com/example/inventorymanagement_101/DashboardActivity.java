package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DashboardActivity extends AppCompatActivity {

    ImageButton btn_add, btn_search, btn_newCustomer, btn_info, btn_graph;
    CardView cardHome,cardSearch, cardInfo, cardBill, cardStats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        cardHome = findViewById(R.id.cardHome);
        cardSearch = findViewById(R.id.cardSearch);
        cardInfo = findViewById(R.id.cardInfo);
        cardBill = findViewById(R.id.cardBill);
        cardStats = findViewById(R.id.cardStats);

        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //move to dashboard2
                startActivity(new Intent(DashboardActivity.this, Dashborad2Activity.class));
            }
        });

        cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //move to searchActivity
                startActivity(new Intent(DashboardActivity.this, SearchActivity.class));
            }
        });

        cardInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //move to InventoryControl Activity
                startActivity(new Intent(DashboardActivity.this, InventoryControlActivity.class));
            }
        });

        cardBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //move to newCustomerActivity
                startActivity(new Intent(DashboardActivity.this, NewCustomerActivity.class));
            }
        });

        cardStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DashboardActivity.this, GraphActivity.class));
            }
        });
    }
}