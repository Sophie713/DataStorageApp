package com.example.acer.datastorageapp.utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.datastorageapp.EditActivity;
import com.example.acer.datastorageapp.R;
import com.example.acer.datastorageapp.data.ProductObject;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<ProductObject> newsList;

    public ProductsAdapter(List newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(newsList.get(position));
        final ProductObject view = newsList.get(position);
        final String product_name = view.getProduct_name();
        final String price = String.valueOf(view.getPrice());
        final String quantity = String.valueOf(view.getQuantity());
        final String supplier = view.getSupplier();
        final String supplier_number = view.getSupplier_number();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditActivity.class);
                intent.putExtra("product_name", product_name);
                intent.putExtra("price", price);
                intent.putExtra("quantity", quantity);
                intent.putExtra("supplier", supplier);
                intent.putExtra("supplier_number", supplier_number);
                v.getContext().startActivity(intent);
            }
        });

        holder.product.setText(product_name);
        holder.price.setText(price);
        holder.quantity.setText(quantity);
        holder.supplier.setText(supplier);
        holder.supplier_number.setText(supplier_number);

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView product;
        public TextView price;
        public TextView quantity;
        public TextView supplier;
        public TextView supplier_number;

        public ViewHolder(View itemView) {
            super(itemView);

            product = itemView.findViewById(R.id.rw_item_product);
            price = itemView.findViewById(R.id.rw_item_price);
            quantity = itemView.findViewById(R.id.rw_item_quantity);
            supplier = itemView.findViewById(R.id.rw_item_supplier);
            supplier_number = itemView.findViewById(R.id.rw_item_supplier_phone);
        }
    }

}