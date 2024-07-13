package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.database.OrderRepository;
import com.example.myapplication.model.Order;

import java.util.List;

public class OrderHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList; // Populate this list with your data
    private OrderRepository orderRepository;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        ivBack = findViewById(R.id.iv_arrow);
        ivBack.setOnClickListener(v -> onBackPressed());

        orderRepository = new OrderRepository(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "1");

        orderList = orderRepository.getOrders(Integer.parseInt(userId));

        recyclerView = findViewById(R.id.recycler_view);
        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadOrders();
    }

    private void loadOrders() {
        orderAdapter.setOrderList(orderList);
    }
}
