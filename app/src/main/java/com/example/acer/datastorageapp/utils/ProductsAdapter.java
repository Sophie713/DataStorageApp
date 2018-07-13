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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        holder.product.setText(view.getProduct_name());
        holder.price.setText(String.valueOf(view.getPrice()));
        holder.quantity.setText(String.valueOf(view.getQuantity()));
        holder.supplier.setText(view.getSupplier());
        holder.supplier_number.setText(view.getSupplier_number());

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
