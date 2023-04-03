package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
//import android.se.omapi.Session;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class NewCustomerActivity extends AppCompatActivity {

    EditText et_customerName, et_phone, et_email, et_customerProduct, et_customerQuantity, et_bill;
    Button btn_addToBill, btn_total, btn_sendBill;
    TextView tv_total;
    int totalSum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        ActivityCompat.requestPermissions(NewCustomerActivity.this,
                new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        et_customerName = findViewById(R.id.et_customerName);
        et_phone = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);
        et_customerProduct = findViewById(R.id.et_customerProduct);
        et_customerQuantity = findViewById(R.id.et_customerQuantity);
        et_bill = findViewById(R.id.et_bill);
        btn_addToBill = findViewById(R.id.btn_addToBill);
        btn_total = findViewById(R.id.btn_total);
        btn_sendBill = findViewById(R.id.btn_sendBill);
        tv_total = findViewById(R.id.tv_total);

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
                        et_bill.append(et_customerProduct.getText().toString()
                                + " " + et_customerQuantity.getText().toString()
                                + " " + product.getPrice() + "\n");

                        totalSum += Integer.parseInt(et_customerQuantity.getText().toString()) * product.getPrice();
                    }
                    else {
                        Toast.makeText(NewCustomerActivity.this, "Not Sufficient Quantity Available", Toast.LENGTH_SHORT).show();
                    }
                }

                et_customerProduct.setText("");
                et_customerQuantity.setText("");
            }
        });

        btn_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = "Dear " + et_customerName.getText().toString() + ", Thanks for buying:\n" + et_bill.getText().toString();
//                String bill = ;
//                bill = str + bill;

                et_bill.setText(str);

                tv_total.setText("Rs " + String.valueOf(totalSum));
                et_bill.append("\nTotal Paid Amount: Rs " + String.valueOf(totalSum));
            }
        });

        btn_sendBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = et_bill.getText().toString();
                String number = et_phone.getText().toString();

                if(number.equals("")) {
                    Toast.makeText(NewCustomerActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                }
                else {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, message, null, null);

                    Toast.makeText(NewCustomerActivity.this, "Bill Sent Successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}