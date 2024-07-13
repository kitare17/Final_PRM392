package com.example.myapplication.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.OrderDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Order;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
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
        holder.orderNameTextView.setText("Order "+ order.getOrderId());
        holder.orderAmountTextView.setText("");

        holder.orderCreateDateTextView.setText(order.getCreateDate());

        Picasso.get()
                .load("https://cdn-icons-png.flaticon.com/512/6632/6632848.png")
                .into(holder.orderImageView);

        holder.orderImageView.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
            intent.putExtra("orderId", order.getOrderId()+"");
            intent.putExtra("address", order.getAddress()+"");
            intent.putExtra("phone", order.getPhone()+"");
            v.getContext().startActivity(intent);
            Toast.makeText(v.getContext(),"ok",Toast.LENGTH_SHORT).show();

        });
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
