package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Order;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> orderList;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public OrderAdapter(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View orderView = inflater.inflate(R.layout.item_order, parent, false);
        return new ViewHolder(orderView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderNameTextView.setText(order.getProductName());
        holder.orderAmountTextView.setText(String.valueOf(order.getAmount()));
        holder.orderCreateDateTextView.setText(order.getCreateDate());
        holder.itemView.setOnClickListener(v -> listener.onOrderClick(order));
        Picasso.get()
                .load(order.getImageUrl())
                .into(holder.orderImageView);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView orderImageView;
        public TextView orderNameTextView;
        public TextView orderAmountTextView;
        public TextView orderCreateDateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            orderImageView = itemView.findViewById(R.id.order_image);
            orderNameTextView = itemView.findViewById(R.id.order_name);
            orderAmountTextView = itemView.findViewById(R.id.order_amount);
            orderCreateDateTextView = itemView.findViewById(R.id.order_create_date);
        }
    }
}
