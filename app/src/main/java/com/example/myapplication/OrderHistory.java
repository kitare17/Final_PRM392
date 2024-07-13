package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.database.OrderRepository;
import com.example.myapplication.database.UserInfoRepository;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.UserInfo;

import java.util.List;
public class OrderHistory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
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
        String googleId = sharedPreferences.getString("googleId", "googleId");
        UserInfoRepository userInfoRepository = new UserInfoRepository(getApplicationContext());
        UserInfo userInfo = userInfoRepository.getUserByIdGoogle(googleId);

        orderList = orderRepository.getOrdersForUser(Integer.parseInt(userInfo.getUserId()));

        recyclerView = findViewById(R.id.recycler_view);
        orderAdapter = new OrderAdapter(orderList, order -> {
            Intent intent = new Intent(OrderHistory.this, OrderDetailActivity.class);
            intent.putExtra("orderId", order.getOrderId());
            startActivity(intent);
        });
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}