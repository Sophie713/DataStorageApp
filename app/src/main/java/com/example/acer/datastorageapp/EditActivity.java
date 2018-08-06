package com.example.acer.datastorageapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.acer.datastorageapp.data.ProductContract;
import com.example.acer.datastorageapp.utils.ProductsDatabaseHelper;

public class EditActivity extends AppCompatActivity {

    ProductsDatabaseHelper helper;
    private EditText product;
    private EditText price;
    private EditText quantity;
    Intent intent;
    private Spinner supplier_spinner;
    private int selected_supplier;
    private EditText supplier_number;
    //values for changing
    private int id;
    private String supplier;
    //buttons
    private ImageView increment;
    private FloatingActionButton fab;
    private ImageView decrement;
    private ImageView delete;
    private ImageView call;
    String[] suppliers = new String[]{"unknown", "Google", "Udacity", "Facebook", "Youtube"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //get buttons
        fab = findViewById(R.id.editing_save);
        increment = findViewById(R.id.editing_increase_quantity);
        decrement = findViewById(R.id.editing_decrease_quantity);
        delete = findViewById(R.id.editing_delete);
        call = findViewById(R.id.editing_call);
        //get views
        price = findViewById(R.id.editing_price);
        product = findViewById(R.id.editing_product_name);
        quantity = findViewById(R.id.editing_quantity);
        supplier_spinner = findViewById(R.id.editing_supplier);
        supplier_number = findViewById(R.id.editing_supplier_phone);
        //
        helper = new ProductsDatabaseHelper(this);
        //view setup
        try {
            //catch intent
            intent = getIntent();
            //setup views
            id = intent.getIntExtra("id", -1);
            if (id == -1) {
                Toast.makeText(this, "There has been some error.", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
            product.setText(intent.getStringExtra("product_name"));
            String fullPrice = (intent.getStringExtra("price"));
            //make price common number
            String finalPrice = fullPrice.substring(0, (fullPrice.length() - 2));
            price.setText(finalPrice);
            quantity.setText(intent.getStringExtra("quantity"));
            supplier = intent.getStringExtra("supplier");
            supplier_number.setText(intent.getStringExtra("supplier_number"));

        } catch (Exception e) {
            Log.e("xyz", e.toString());
        }
        //SET ON CLICK LISTENERS
        //set up the save button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAndInsert();
                onBackPressed();
            }
        });
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i = Integer.valueOf(quantity.getText().toString());
                    i++;
                    quantity.setText(String.valueOf(i));
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i = Integer.valueOf(quantity.getText().toString());
                    if (i > 0) {
                        i--;
                        quantity.setText(String.valueOf(i));
                    } else {
                        Toast.makeText(v.getContext(), "No more products to sell.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().getContentResolver().delete(ProductContract.ProductEntry.CONTENT_URI, (ProductContract.ProductEntry._ID + "=?"), new String[]{String.valueOf(id)});
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uri = "tel:" + supplier_number.getText().toString().trim();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "Couldn't make a call.", Toast.LENGTH_SHORT).show();
                }
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
                supplier_spinner.setSelection(setUpSpinner(supplier));
            }
        });
        //choose corresponding supplier
        supplier_spinner.setSelection(setUpSpinner(supplier));
    }

    private void editAndInsert() {
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.PRODUCT_NAME, product.getText().toString().trim());
        values.put(ProductContract.ProductEntry.PRICE, Integer.valueOf(price.getText().toString().trim()));
        values.put(ProductContract.ProductEntry.QUANTITY, Integer.valueOf(quantity.getText().toString().trim()));
        values.put(ProductContract.ProductEntry.SUPPLIER_NAME, selected_supplier);
        values.put(ProductContract.ProductEntry.SUPPLIER_PHONE, supplier_number.getText().toString().trim());
        //insert
        getApplicationContext().getContentResolver().update(ProductContract.ProductEntry.CONTENT_URI, values, (ProductContract.ProductEntry._ID + "=?"), new String[]{String.valueOf(id)});
        finish();
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
