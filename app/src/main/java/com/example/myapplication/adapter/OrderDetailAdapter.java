package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ProductCart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private List<ProductCart> productList;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(int productId);
    }

    public OrderDetailAdapter(List<ProductCart> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCart product = productList.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.amountTextView.setText("Quantity: " + product.getAmount());
        holder.priceTextView.setText("Price: $" + product.getPrice());
        Picasso.get().load(product.getImageUrl()).into(holder.productImageView);

        holder.itemView.setOnClickListener(v -> listener.onProductClick(product.getId()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView, amountTextView, priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image);
            productNameTextView = itemView.findViewById(R.id.product_name);
            amountTextView = itemView.findViewById(R.id.amount);
            priceTextView = itemView.findViewById(R.id.price);
        }
    }
}
