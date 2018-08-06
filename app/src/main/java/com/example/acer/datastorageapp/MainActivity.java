package com.example.acer.datastorageapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
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
    private static final int loaderID = 0;
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
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.main_activity_listView);

        getLoaderManager().initLoader(loaderID, null, this);

        fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        productsAdapter = new ProductsAdapter(this, null);
        listView.setAdapter(productsAdapter);
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        ProductsDatabaseHelper productsDatabaseHelper = new ProductsDatabaseHelper(this);
        productList = productsDatabaseHelper.productList();

        Cursor cursor = getContentResolver().query(CONTENT_URI, projection, null, null, null);
        try {
            TextView displayView = findViewById(R.id.test);
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
            productsAdapter.swapCursor(data);

        } else {
            Toast.makeText(this, "wtf", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productList.clear();
        listView.removeAllViews();
        getLoaderManager().initLoader(loaderID, null, this);
        productsAdapter.swapCursor(null);
    }
}
