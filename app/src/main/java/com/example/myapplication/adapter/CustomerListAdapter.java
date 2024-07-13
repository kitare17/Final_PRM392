package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {

    private List<String> customerIds;
    private OnItemClickListener listener;

    // Constructor with both customerIds and listener
    public CustomerListAdapter(List<String> customerIds, OnItemClickListener listener) {
        this.customerIds = customerIds;
        this.listener = listener;
    }

    // Constructor with only customerIds
    public CustomerListAdapter(List<String> customerIds) {
        this.customerIds = customerIds;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        String customerId = customerIds.get(position);
        holder.bind(customerId, listener);
    }

    @Override
    public int getItemCount() {
        if (customerIds == null) {
            return 0;
        }
        return customerIds.size();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewDisplayName);
        }

        void bind(final String customerId, final OnItemClickListener listener) {
            textView.setText(customerId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(customerId);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String customerId);
    }
}
