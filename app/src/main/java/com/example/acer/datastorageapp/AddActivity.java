package com.example.acer.datastorageapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
                onBackPressed();
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

            }
        });
    }

    private void checkAndInsert() {
        /**String typed_product = "";
        int typed_price = 0;
        int typed_quantity = 0;
        String typed_supplier_number = "";
        //get input
        typed_product = product.getText().toString();
        typed_price = Integer.valueOf(price.getText().toString());
        typed_quantity = Integer.valueOf(quantity.getText().toString());
        typed_supplier_number = supplier_number.getText().toString();*/
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.PRODUCT_NAME, product.getText().toString());
        values.put(ProductContract.ProductEntry.PRICE, Integer.valueOf(price.getText().toString()));
        values.put(ProductContract.ProductEntry.QUANTITY, Integer.valueOf(quantity.getText().toString()));
        values.put(ProductContract.ProductEntry.SUPPLIER_NAME, selected_supplier);
        values.put(ProductContract.ProductEntry.SUPPLIER_PHONE, supplier_number.getText().toString());
        //insert
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);

    }
}
/**
 *         //insert string
 String INSERT = "insert into " + ProductContract.ProductEntry.TABLE_NAME + "(" +
 ProductContract.ProductEntry.PRODUCT_NAME + ", " +
 ProductContract.ProductEntry.PRICE + ", " +
 ProductContract.ProductEntry.QUANTITY + ", " +
 ProductContract.ProductEntry.SUPPLIER_NAME + ", " +
 ProductContract.ProductEntry.SUPPLIER_PHONE + ") VALUES (" +
 typed_product + ", " +
 typed_price + ", " +
 typed_quantity + ", " +
 selected_supplier + ", " +
 typed_supplier_number + ");";
 */
