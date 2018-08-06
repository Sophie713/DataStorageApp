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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.acer.datastorageapp.data.ProductContract;
import com.example.acer.datastorageapp.utils.ProductsAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int loaderID = 0;
    ProductsAdapter productsAdapter;
    FloatingActionButton fab;
    LinearLayout noInfo;
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
        noInfo = findViewById(R.id.main_activity_nothing_to_display);
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

        getLoaderManager().initLoader(loaderID, null, this);
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
        if (data != null && data.getCount() > 0) {
            listView.setVisibility(View.VISIBLE);
            noInfo.setVisibility(View.GONE);
            productsAdapter.swapCursor(data);
        } else {
            listView.setVisibility(View.INVISIBLE);
            noInfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productsAdapter.swapCursor(null);
    }
}
