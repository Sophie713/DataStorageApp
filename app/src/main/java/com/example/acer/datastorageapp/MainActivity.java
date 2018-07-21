package com.example.acer.datastorageapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.datastorageapp.data.ProductContract;
import com.example.acer.datastorageapp.data.ProductObject;
import com.example.acer.datastorageapp.utils.ProductsAdapter;
import com.example.acer.datastorageapp.utils.ProductsDatabaseHelper;

import java.util.List;

import static com.example.acer.datastorageapp.data.ProductContract.ProductEntry.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    List<ProductObject> productList;
    FloatingActionButton fab;
    String[] projection = {
            ProductContract.ProductEntry._ID,
            ProductContract.ProductEntry.PRODUCT_NAME,
            ProductContract.ProductEntry.PRICE,
            ProductContract.ProductEntry.QUANTITY,
            ProductContract.ProductEntry.SUPPLIER_NAME,
            ProductContract.ProductEntry.SUPPLIER_PHONE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!productList.isEmpty()) {
            ProductsAdapter adapter = new ProductsAdapter(productList);

            RecyclerView myListView = findViewById(R.id.main_activity_recyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

            //TODO: create function for the sold button

            myListView.setLayoutManager(layoutManager);
            myListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "wtf", Toast.LENGTH_LONG).show();
        }



}

    private void displayDatabaseInfo() {
        ProductsDatabaseHelper productsDatabaseHelper = new ProductsDatabaseHelper(this);
        ProductsDatabaseHelper mDbHelper = new ProductsDatabaseHelper(this);
        productList = productsDatabaseHelper.productList();

        Cursor cursor = getContentResolver().query(CONTENT_URI, projection, null, null, null);
        try {
            TextView displayView = (TextView) findViewById(R.id.test);
            displayView.setText("Number of rows: " + cursor.getCount());
        } finally {
            cursor.close();
        }
    }

}
