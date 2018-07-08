package com.example.acer.datastorageapp.utils;

import android.content.Context;
import com.example.acer.datastorageapp.data.ProductContract;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    public ProductsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductContract.ProductEntry.TABLE_NAME + " (" +
                ProductContract.ProductEntry._ID + " INTEGER primary key autoincrement, " +
                ProductContract.ProductEntry.PRODUCT_NAME + " string not null, " +
                ProductContract.ProductEntry.PRICE + " integer not null, " +
                ProductContract.ProductEntry.QUANTITY + " integer default 0, " +
                ProductContract.ProductEntry.SUPPLIER_NAME + " integer, " +
                ProductContract.ProductEntry.SUPPLIER_PHONE + " string);";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
