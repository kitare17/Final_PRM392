package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.OrderDetailAdapter;
import com.example.myapplication.database.OrderRepository;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.ProductCart;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderDetailAdapter orderDetailAdapter;
    private List<ProductCart> productList;
    private OrderRepository orderRepository;
    private TextView orderIdTextView, createDateTextView, addressTextView, phoneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        int orderId = getIntent().getIntExtra("orderId", -1);
        if (orderId == -1) {
            finish();
            return;
        }

        orderRepository = new OrderRepository(this);
        productList = orderRepository.getOrderDetails(orderId);

        orderIdTextView = findViewById(R.id.order_id);
        createDateTextView = findViewById(R.id.create_date);
        addressTextView = findViewById(R.id.address);
        phoneTextView = findViewById(R.id.phone);

        // Set order details
        Order order = orderRepository.getOrderById(orderId);
        orderIdTextView.setText("Order #" + order.getOrderId());
        createDateTextView.setText("Date: " + order.getCreateDate());
        addressTextView.setText("Address: " + order.getAddress());
        phoneTextView.setText("Phone: " + order.getPhone());

        recyclerView = findViewById(R.id.recycler_view);
        orderDetailAdapter = new OrderDetailAdapter(productList, productId -> {
            Intent intent = new Intent(OrderDetailActivity.this, ProductDetailActivity.class);
            intent.putExtra("productId", String.valueOf(productId));
            startActivity(intent);
        });
        recyclerView.setAdapter(orderDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}