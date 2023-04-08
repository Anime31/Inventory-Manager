package com.example.inventorymanagement_101;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class Dashborad2Activity extends AppCompatActivity {

    EditText et_productName, et_quantity, et_price, et_threshold;
    Button btn_viewAll, btn_Add;
    ListView lv_productList;
    ArrayAdapter productArrayAdapter;
    DatabaseHelper databaseHelper;
    DatePickerDialog datePickerDialog;
    Button btn_expiryDatePicker;

    private int addedDateInt, expiryDateInt;    //yyyymmdd

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
        btn_expiryDatePicker = findViewById(R.id.btn_expiryDatePicker);
        lv_productList = findViewById(R.id.lv_productList);

        databaseHelper = new DatabaseHelper(Dashborad2Activity.this);

        ShowProductOnListView(databaseHelper);

        initDatePicker();
        btn_expiryDatePicker.setText(getTodaysDate());


//        System.out.println(btn_expiryDatePicker.getText().toString());

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
                             Integer.parseInt(et_threshold.getText().toString()),
                             addedDateInt,
                             expiryDateInt, 0);

                    Toast.makeText(Dashborad2Activity.this, productModel.toString(), Toast.LENGTH_SHORT).show();


                    DatabaseHelper databaseHelper = new DatabaseHelper(Dashborad2Activity.this);

                    // If product is present then increment the quantity
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

                    //if quantity is 0 then delete the product
                    if(productModel1.getQuantity()==0) {
                        System.out.println(productModel1.getQuantity());
                        databaseHelper.deleteOne(productModel1);
                    }

                }
                catch (Exception e) {
                    Toast.makeText(Dashborad2Activity.this, "Error Creating product", Toast.LENGTH_SHORT).show();
//                    productModel = new productModel(-1, "error", 0,0,0,currentDate(),currentDate(),0);
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

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        addedDateInt = year*10000 + month*100 + day;
        expiryDateInt = year*10000 + month*100 + day;

//        System.out.println(addedDateInt);
//        System.out.println(expiryDateInt);

        return makeDateString(day, month, year);
    }

    int currentDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return year*10000 + month*100 + day;
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;

                expiryDateInt = year*10000 + month*100 + day;
//                System.out.println(expiryDateInt);

                String date = makeDateString(day, month, year);
                btn_expiryDatePicker.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.R.style.Theme_Holo_Light_Dialog_MinWidth;

        datePickerDialog = new DatePickerDialog(Dashborad2Activity.this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);   //subtracted 1 sec from current time

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

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}