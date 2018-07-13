package com.example.acer.datastorageapp.utils;

import android.content.Context;

import com.example.acer.datastorageapp.MainActivity;
import com.example.acer.datastorageapp.data.ProductContract;
import com.example.acer.datastorageapp.data.ProductObject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ProductsDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String SELECT_ALL = "SELECT * FROM " + ProductContract.ProductEntry.TABLE_NAME;
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    public ProductsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    public List<ProductObject> productList() {
        //myVariables;
        /** int _id;
         String product_name;
         int price;
         int quantity;
         int supplier_id;
         String supplier_number;
         String supplier;*/
        List<ProductObject> list = new ArrayList<>();

        ProductsDatabaseHelper mDbHelper = new ProductsDatabaseHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(SELECT_ALL, null);
            /** int remainingRows = cursor.getCount();
             for(int i = 1; i<=remainingRows; i++){
             String id = String.valueOf(i);
             db.rawQuery(SELECT_ALL + " WHERE _ID = " + id, null).toString();
             }*/
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry._ID));
                    String product = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.PRODUCT_NAME));
                    int price = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.PRICE));
                    int quantity = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.QUANTITY));
                    int supplier = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.SUPPLIER_NAME));
                    String supplier_phone = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.SUPPLIER_PHONE));
                    list.add(id - 1, new ProductObject(product, price, quantity, supplier, supplier_phone));
                    // do what ever you want here
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("xyz", e.toString());
        }
        return list;
    }
}
