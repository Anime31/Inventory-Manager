package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init
        TextView username = (TextView) findViewById(R.id.editTextUsername);
        TextView password = (TextView) findViewById(R.id.editTextPassword);

        Button loginbtn = (Button) findViewById(R.id.btnLogin);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    //authorize
                    startActivity(new Intent(LoginActivity.this, Dashborad2Activity.class));

                }
                else {
                    //don't authorize
                    Toast.makeText(LoginActivity.this, "Login Failed!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}