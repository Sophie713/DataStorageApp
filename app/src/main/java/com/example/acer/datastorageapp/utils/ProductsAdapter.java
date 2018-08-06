package com.example.acer.datastorageapp.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.datastorageapp.EditActivity;
import com.example.acer.datastorageapp.R;
import com.example.acer.datastorageapp.data.ProductContract;

public class ProductsAdapter extends CursorAdapter {

    public ProductsAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        return super.swapCursor(newCursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        try {
            //find views
            TextView productTV = view.findViewById(R.id.rw_item_product);
            TextView priceTV = view.findViewById(R.id.rw_item_price);
            TextView quantityTV = view.findViewById(R.id.rw_item_quantity);
            TextView supplierTV = view.findViewById(R.id.rw_item_supplier);
            TextView supplier_numberTV = view.findViewById(R.id.rw_item_supplier_phone);
            Button sellProduct = view.findViewById(R.id.rw_item_button);
            //get indexes
            int idColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry._ID);
            int productColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.SUPPLIER_PHONE);
            //get values
            final int id = cursor.getInt(idColumnIndex);
            final String productName = cursor.getString(productColumnIndex);
            final String price = cursor.getString(priceColumnIndex) + ",-";
            final int quantity = cursor.getInt(quantityColumnIndex);
            final String supplier = getSupplierName(cursor.getString(supplierColumnIndex));
            final String supplier_phone = cursor.getString(supplierPhoneColumnIndex);
            //setup Views
            productTV.setText(productName);
            priceTV.setText(price);
            quantityTV.setText(String.valueOf(quantity));
            supplierTV.setText(supplier);
            if (TextUtils.isEmpty(supplier_phone)) {
                supplier_numberTV.setText(R.string.no_contact);
            } else {
                supplier_numberTV.setText(supplier_phone);
            }
            //setOnClickListener to decrement quantity
            sellProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newQuantity = quantity - 1;
                    if (newQuantity >= 0) {
                        ContentValues values = new ContentValues();
                        values.put(ProductContract.ProductEntry.QUANTITY, newQuantity);
                        v.getContext().getContentResolver().update(ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI, id), values, (ProductContract.ProductEntry._ID + "=?"), new String[]{String.valueOf(id)});
                    } else {
                        Toast.makeText(v.getContext(), R.string.nothing_to_sell, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EditActivity.class);
                    intent.putExtra("product_name", productName);
                    intent.putExtra("id", id);
                    intent.putExtra("price", price);
                    intent.putExtra("quantity", String.valueOf(quantity));
                    intent.putExtra("supplier", supplier);
                    intent.putExtra("supplier_number", supplier_phone);
                    v.getContext().startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.e("xyz", e.toString());
        }
    }

    public String getSupplierName(String supplierId) {
        String supplier;
        switch (supplierId) {
            case "1":
                supplier = "Google";
                break;
            case "2":
                supplier = "Udacity";
                break;
            case "3":
                supplier = "Facebook";
                break;
            case "4":
                supplier = "Youtube";
                break;
            default:
                supplier = "Unknown supplier";
                break;
        }
        return supplier;

    }
}