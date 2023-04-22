package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class FilterActivity extends AppCompatActivity {

    CheckBox cb_quantity, cb_price, cb_expiry;
    EditText et_minQuantity, et_maxQuantity, et_minPrice, et_maxPrice;
    Button btn_expiryAfter, btn_expiryBefore, btn_clear, btn_done;
    DatePickerDialog datePickerDialog1, datePickerDialog2;
    int minExpiryDateInt, maxExpiryDateInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        cb_quantity = findViewById(R.id.cb_quantity);
        cb_price = findViewById(R.id.cb_price);
        cb_expiry = findViewById(R.id.cb_expiry);
        et_minQuantity = findViewById(R.id.et_minQuantity);
        et_maxQuantity = findViewById(R.id.et_maxQuantity);
        et_minPrice = findViewById(R.id.et_minPrice);
        et_maxPrice = findViewById(R.id.et_maxPrice);
        btn_expiryAfter = findViewById(R.id.btn_expiryAfter);
        btn_expiryBefore = findViewById(R.id.btn_expiryBefore);
        btn_clear = findViewById(R.id.btn_clear);
        btn_done = findViewById(R.id.btn_done);

        initDatePicker1();
        initDatePicker2();
        btn_expiryAfter.setText(getTodaysDate());
        btn_expiryBefore.setText(getTwentyYearsAfterTodaysDate());

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_quantity.isChecked()){
                    cb_quantity.toggle();
                }
                if(cb_price.isChecked()){
                    cb_price.toggle();
                }
                if(cb_expiry.isChecked()){
                    cb_expiry.toggle();
                }
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FilterActivity.this, SearchActivity.class);

                if(cb_quantity.isChecked()){
                    intent.putExtra("minQuantity", Integer.parseInt(et_minQuantity.getText().toString().equals("") ? "0" : et_minQuantity.getText().toString()));
                    intent.putExtra("maxQuantity", Integer.parseInt(et_maxQuantity.getText().toString().equals("") ? "2147483647" : et_maxQuantity.getText().toString()));
                }
                if(cb_price.isChecked()){
                    intent.putExtra("minPrice", Integer.parseInt(et_minPrice.getText().toString().equals("") ? "0" : et_minPrice.getText().toString()));
                    intent.putExtra("maxPrice", Integer.parseInt(et_maxPrice.getText().toString().equals("") ? "2147483647" : et_maxPrice.getText().toString()));
                }
                if(cb_expiry.isChecked()){
                    intent.putExtra("minExpiry", minExpiryDateInt);
                    intent.putExtra("maxExpiry", maxExpiryDateInt);
                }

                startActivity(intent);
            }
        });


    }

    //On click listeners
    public void openDatePicker1(View view)
    {
        datePickerDialog1.show();
    }
    public void openDatePicker2(View view)
    {
        datePickerDialog2.show();
    }

    private void initDatePicker1()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;

                minExpiryDateInt = year*10000 + month*100 + day;

                String date = makeDateString(day, month, year);

                btn_expiryAfter.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.R.style.Theme_Holo_Light_Dialog_MinWidth;

        datePickerDialog1 = new DatePickerDialog(FilterActivity.this, style, dateSetListener, year, month, day);
//        datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);   //subtracted 1 sec from current time

    }
    private void initDatePicker2()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;

                maxExpiryDateInt = year*10000 + month*100 + day;

                String date = makeDateString(day, month, year);

                btn_expiryBefore.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.R.style.Theme_Holo_Light_Dialog_MinWidth;

        datePickerDialog2 = new DatePickerDialog(FilterActivity.this, style, dateSetListener, year, month, day);
//        datePickerDialog2.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);   //subtracted 1 sec from current time

    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        minExpiryDateInt = year*10000 + month*100 + day;

        return makeDateString(day, month, year);
    }
    private String getTwentyYearsAfterTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR) + 20;
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        maxExpiryDateInt = year*10000 + month*100 + day;

        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

}