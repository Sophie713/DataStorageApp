package com.example.acer.datastorageapp.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
        //GET ID
        final int currentId = cursor.getColumnIndex(ProductContract.ProductEntry._ID);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "I work!", Toast.LENGTH_SHORT).show();
            }
        });
        TextView product = view.findViewById(R.id.rw_item_product);
        TextView price = view.findViewById(R.id.rw_item_price);
        final TextView quantity = view.findViewById(R.id.rw_item_quantity);
        TextView supplier = view.findViewById(R.id.rw_item_supplier);
        TextView supplier_number = view.findViewById(R.id.rw_item_supplier_phone);
        Button sellProduct = view.findViewById(R.id.rw_item_button);
        //setOnClickListener
        sellProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = Integer.valueOf(quantity.getText().toString()) - 1;
                if (newQuantity >= 0) {
                    //TODO update table
                    ContentValues values = new ContentValues(currentId);
                    values.put(ProductContract.ProductEntry.QUANTITY, newQuantity);
                    Uri myUri = ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI, currentId);
                    v.getContext().getContentResolver().update(myUri, values, null, null);
                } else {
                    Toast.makeText(v.getContext(), "No more items to sell", Toast.LENGTH_SHORT).show();
                }
            }
        });

        int productColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.QUANTITY);
        int supplierColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.SUPPLIER_NAME);
        int supplierPhoneColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.SUPPLIER_PHONE);


        product.setText(cursor.getString(productColumnIndex));
        price.setText(cursor.getString(priceColumnIndex) + ",-");
        quantity.setText(cursor.getString(quantityColumnIndex));
        supplier.setText(getSupplierName(cursor.getString(supplierColumnIndex)));
        if (TextUtils.isEmpty(cursor.getString(supplierPhoneColumnIndex))) {
            supplier_number.setText(R.string.no_contact);
        } else {
            supplier_number.setText(cursor.getString(supplierPhoneColumnIndex));
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

