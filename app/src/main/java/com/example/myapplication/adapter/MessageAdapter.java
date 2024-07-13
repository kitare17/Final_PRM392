package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.model.Message;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMessage;
        TextView textViewTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }

        public void bind(Message message) {
            textViewMessage.setText(message.getMessage());
            // The timestamp in milliseconds
            long timestamp = message.getTimestamp();

            // Convert the timestamp to an Instant
            Instant instant = Instant.ofEpochMilli(timestamp);

            // Define the desired time zone
            ZoneId zoneId = ZoneId.systemDefault(); // or specify a specific time zone, e.g., ZoneId.of("America/New_York")

            // Convert the Instant to a ZonedDateTime in the specified time zone
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, zoneId);

            // Print the result
            System.out.println("Local Date and Time: " + dateTime);
            textViewTime.setText(dateTime.getHour()+":"+dateTime.getMinute());
        }
    }
}


