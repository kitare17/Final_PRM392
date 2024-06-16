package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList; // Assuming a Product class exists
    private final LayoutInflater inflater;

    // Constructor
    public ProductAdapter(List<Product> productList, LayoutInflater inflater) {
        this.productList = productList;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product != null) {
            holder.productName.setText(product.getName());
            holder.productType.setText(product.getType());
            holder.productPrice.setText("$" + String.format("%.2f", product.getPrice()));
            holder.productImage.setImageResource(product.getImageUrl());
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder inner class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productType = itemView.findViewById(R.id.productType);
        }
    }
}
