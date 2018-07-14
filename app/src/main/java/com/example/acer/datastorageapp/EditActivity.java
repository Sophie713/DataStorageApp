package com.example.acer.datastorageapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.acer.datastorageapp.data.ProductContract;
import com.example.acer.datastorageapp.utils.ProductsDatabaseHelper;

public class EditActivity extends AppCompatActivity {

    private TextView info;
    private EditText product;
    private EditText price;
    private EditText quantity;
    private Spinner supplier_spinner;
    private int selected_supplier;
    private EditText supplier_number;
    private FloatingActionButton fab;
    ProductsDatabaseHelper helper;Intent intent;
    String[] suppliers = new String[]{"unknown", "Google", "Udacity", "Facebook", "Youtube"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        try {
            //catch intent
            intent = getIntent();
            //get views
            info = findViewById(R.id.editing_id_text);

            product = findViewById(R.id.editing_product_name);
            product.setText(intent.getStringExtra("product_name"));

            price = findViewById(R.id.editing_price);
            price.setText(intent.getStringExtra("price"));

            quantity = findViewById(R.id.editing_quantity);
            quantity.setText(intent.getStringExtra("quantity"));

            supplier_spinner = findViewById(R.id.editing_supplier);


            supplier_number = findViewById(R.id.editing_supplier_phone);
            supplier_number.setText(intent.getStringExtra("supplier_number"));

        } catch (Exception e) {
            Log.e("xyz", e.toString());
        }
        fab = findViewById(R.id.editing_save);
        helper = new ProductsDatabaseHelper(this);
        //set up the save button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAndInsert();
                onBackPressed();
            }
        });
        //set up the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, suppliers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supplier_spinner.setAdapter(adapter);
        supplier_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        selected_supplier = ProductContract.ProductEntry.SUPPLIER_GOOGLE;
                        break;
                    case 2:
                        selected_supplier = ProductContract.ProductEntry.SUPPLIER_UDACITY;
                        break;
                    case 3:
                        selected_supplier = ProductContract.ProductEntry.SUPPLIER_FACEBOOK;
                        break;
                    case 4:
                        selected_supplier = ProductContract.ProductEntry.SUPPLIER_YOUTUBE;
                        break;
                    default:
                        selected_supplier = ProductContract.ProductEntry.SUPPLIER_UNKNOWN;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                supplier_spinner.setSelection(setUpSpinner(intent.getStringExtra("supplier")));
            }
        });

        supplier_spinner.setSelection(setUpSpinner(intent.getStringExtra("supplier")));
    }

    private void editAndInsert() {
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.PRODUCT_NAME, product.getText().toString());
        values.put(ProductContract.ProductEntry.PRICE, Integer.valueOf(price.getText().toString()));
        values.put(ProductContract.ProductEntry.QUANTITY, Integer.valueOf(quantity.getText().toString()));
        values.put(ProductContract.ProductEntry.SUPPLIER_NAME, selected_supplier);
        values.put(ProductContract.ProductEntry.SUPPLIER_PHONE, supplier_number.getText().toString());
        //insert
        SQLiteDatabase db = helper.getWritableDatabase();

    }

    public int setUpSpinner(String supplier) {
        int result = 0;
        if (supplier == suppliers[1]) {
            result = 1;
        } else if (supplier == suppliers[2]) {
            result = 2;
        } else if (supplier == suppliers[3]) {
            result = 3;
        } else if (supplier == suppliers[4]) {
            result = 4;
        }
        return result;
    }

}
