package com.example.acer.datastorageapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    List<ProductObject> productList;
    ProductsAdapter productsAdapter;
    FloatingActionButton fab;
    String[] projection = {
            ProductContract.ProductEntry._ID,
            ProductContract.ProductEntry.PRODUCT_NAME,
            ProductContract.ProductEntry.PRICE,
            ProductContract.ProductEntry.QUANTITY,
            ProductContract.ProductEntry.SUPPLIER_NAME,
            ProductContract.ProductEntry.SUPPLIER_PHONE
    };
    private int loaderID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(loaderID, null, this);

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                ProductContract.ProductEntry.CONTENT_URI,
                projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!productList.isEmpty()) {

        } else {
            Toast.makeText(this, "wtf", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productsAdapter.swapCursor(null);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
