package com.example.acer.datastorageapp.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.datastorageapp.R;
import com.example.acer.datastorageapp.data.ProductContract;
import com.example.acer.datastorageapp.data.ProductObject;

import java.util.List;

public class ProductsAdapter extends CursorAdapter {
    private List<ProductObject> productsList;

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
    public void bindView(View view, Context context, final Cursor cursor) {
        //cursor position
        final int position = cursor.getPosition();
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
        String productName = cursor.getString(productColumnIndex);
        String price = cursor.getString(priceColumnIndex) + ",-";
        final int quantity = cursor.getInt(quantityColumnIndex);
        String supplier = getSupplierName(cursor.getString(supplierColumnIndex));
        String supplier_phone = cursor.getString(supplierPhoneColumnIndex);
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
                    Toast.makeText(v.getContext(), "No more items to sell", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "I work!", Toast.LENGTH_SHORT).show();
            }
        });
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


/**
 * @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
 * holder.itemView.setTag(newsList.get(position));
 * final ProductObject view = newsList.get(position);
 * final String product_name = view.getProduct_name();
 * final String price = String.valueOf(view.getPrice());
 * final String quantity = String.valueOf(view.getQuantity());
 * final String supplier = view.getSupplier();
 * final String supplier_number = view.getSupplier_number();
 * <p>
 * holder.itemView.setOnClickListener(new View.OnClickListener() {
 * @Override public void onClick(View v) {
 * Intent intent = new Intent(v.getContext(), EditActivity.class);
 * intent.putExtra("product_name", product_name);
 * intent.putExtra("price", price);
 * intent.putExtra("quantity", quantity);
 * intent.putExtra("supplier", supplier);
 * intent.putExtra("supplier_number", supplier_number);
 * v.getContext().startActivity(intent);
 * }
 * });
 * <p>
 * holder.product.setText(product_name);
 * holder.price.setText(price);
 * holder.quantity.setText(quantity);
 * holder.supplier.setText(supplier);
 * holder.supplier_number.setText(supplier_number);
 * <p>
 * }
 * @Override public int getItemCount() {
 * return newsList.size();
 * }
 * @Override public View newView(Context context, Cursor cursor, ViewGroup parent) {
 * View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
 * return view;
 * }
 * @Override public void bindView(View view, Context context, Cursor cursor) {
 * holder.itemView.setTag(newsList.get(position));
 * final ProductObject view = newsList.get(position);
 * final String product_name = view.getProduct_name();
 * final String price = String.valueOf(view.getPrice());
 * final String quantity = String.valueOf(view.getQuantity());
 * final String supplier = view.getSupplier();
 * final String supplier_number = view.getSupplier_number();
 * <p>
 * holder.itemView.setOnClickListener(new View.OnClickListener() {
 * @Override public void onClick(View v) {
 * Intent intent = new Intent(v.getContext(), EditActivity.class);
 * intent.putExtra("product_name", product_name);
 * intent.putExtra("price", price);
 * intent.putExtra("quantity", quantity);
 * intent.putExtra("supplier", supplier);
 * intent.putExtra("supplier_number", supplier_number);
 * v.getContext().startActivity(intent);
 * }
 * });
 * <p>
 * holder.product.setText(product_name);
 * holder.price.setText(price);
 * holder.quantity.setText(quantity);
 * holder.supplier.setText(supplier);
 * holder.supplier_number.setText(supplier_number);
 * <p>
 * }
 * <p>
 * public class ViewHolder extends RecyclerView.ViewHolder {
 * <p>
 * public TextView product;
 * public TextView price;
 * public TextView quantity;
 * public TextView supplier;
 * public TextView supplier_number;
 * <p>
 * public ViewHolder(View itemView) {
 * super(itemView);
 * <p>
 * product = itemView.findViewById(R.id.rw_item_product);
 * price = itemView.findViewById(R.id.rw_item_price);
 * quantity = itemView.findViewById(R.id.rw_item_quantity);
 * supplier = itemView.findViewById(R.id.rw_item_supplier);
 * supplier_number = itemView.findViewById(R.id.rw_item_supplier_phone);
 * }
 * }
 */

