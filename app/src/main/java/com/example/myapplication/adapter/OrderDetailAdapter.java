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
import com.example.myapplication.model.OrderDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>{

    private List<OrderDetail> orderDetails;

    public OrderDetailAdapter(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setOrderList(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View orderView = inflater.inflate(R.layout.item_order, parent, false);
        return new OrderDetailAdapter.ViewHolder(orderView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.ViewHolder holder, int position) {
        OrderDetail order = orderDetails.get(position);
        holder.orderNameTextView.setText( order.getProductName());
        holder.orderAmountTextView.setText("x"+order.getAmount());

        holder.orderCreateDateTextView.setText("");

        Picasso.get()
                .load(order.getImageUrl())
                .into(holder.orderImageView);
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
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
