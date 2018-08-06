package com.example.acer.datastorageapp;

import android.content.ContentValues;
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
import android.widget.Toast;

import com.example.acer.datastorageapp.data.ProductContract;
import com.example.acer.datastorageapp.utils.ProductsDatabaseHelper;

public class AddActivity extends AppCompatActivity {

    private EditText product;
    private EditText price;
    private EditText quantity;
    private Spinner supplier_spinner;
    private int selected_supplier;
    private EditText supplier_number;
    private FloatingActionButton fab;
    ProductsDatabaseHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //find my views
        product = findViewById(R.id.editor_product_name);
        price = findViewById(R.id.editor_price);
        quantity = findViewById(R.id.editor_quantity);
        supplier_spinner = findViewById(R.id.editor_supplier_name);
        supplier_number = findViewById(R.id.editor_supplier_phone);
        fab = findViewById(R.id.fab_save);
        helper = new ProductsDatabaseHelper(this);
        //set up the save button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndInsert();
            }
        });
        //set up the spinner
        String[] suppliers = new String[]{"unknown", "Google", "Udacity", "Facebook", "Youtube"};
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
                selected_supplier = ProductContract.ProductEntry.SUPPLIER_UNKNOWN;
            }
        });
    }

    private void checkAndInsert() {
        try {
            ContentValues values = new ContentValues();
            values.put(ProductContract.ProductEntry.PRODUCT_NAME, product.getText().toString());
            values.put(ProductContract.ProductEntry.PRICE, Integer.valueOf(price.getText().toString()));
            values.put(ProductContract.ProductEntry.QUANTITY, Integer.valueOf(quantity.getText().toString()));
            values.put(ProductContract.ProductEntry.SUPPLIER_NAME, selected_supplier);
            values.put(ProductContract.ProductEntry.SUPPLIER_PHONE, supplier_number.getText().toString());
            //insert
            this.getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Values are not valid or missing.", Toast.LENGTH_LONG).show();
            Log.e("xyz", e.toString());
        }

    }
}