package com.example.myapplication.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductTest;
import com.squareup.picasso.Picasso;

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
            Picasso.get()
                    .load(product.getImageUrl())
                    .into(holder.productImage);
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder inner class
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, heartIcon;
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
