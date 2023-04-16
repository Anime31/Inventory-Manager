package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InventoryControlActivity extends AppCompatActivity {

    Button btn_perishable, btn_expired, btn_scarce, btn_wasted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_control);

        btn_perishable = findViewById(R.id.btn_perishable);
        btn_expired = findViewById(R.id.btn_expired);
        btn_scarce = findViewById(R.id.btn_scarce);
        btn_wasted = findViewById(R.id.btn_wasted);

        Intent intent = new Intent(InventoryControlActivity.this, InventoryControlHelperActivity.class);
        btn_scarce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("key@1234","Scarce Items");
                startActivity(intent);
            }
        });

        btn_expired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("key@1234","Expired Items");
                startActivity(intent);
            }
        });

        btn_perishable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("key@1234","Perishable Items");
                startActivity(intent);
            }
        });

        btn_wasted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("key@1234","Wasted Items");
                startActivity(intent);
            }
        });
    }
}