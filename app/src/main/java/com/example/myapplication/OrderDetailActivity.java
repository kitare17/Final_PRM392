package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.adapter.OrderDetailAdapter;
import com.example.myapplication.database.OrderRepository;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.OrderDetail;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    TextView fullname;
    TextView address;
    TextView phone;
    private RecyclerView recyclerView;
    private OrderDetailAdapter orderDetailAdapter;
    private List<OrderDetail> orderDetails; // Populate this list with your data
    private OrderRepository orderRepository;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_detail);


        ivBack = findViewById(R.id.iv_arrow);
        fullname = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        ivBack = findViewById(R.id.iv_arrow);
        ivBack.setOnClickListener(v -> onBackPressed());

        orderRepository = new OrderRepository(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String fullnameG = sharedPreferences.getString("fullname", "1");

        String orderId = getIntent().getStringExtra("orderId");
        String addressG = getIntent().getStringExtra("address");
        String phoneG = getIntent().getStringExtra("phone");
        orderDetails = orderRepository.getOrderDetail(Integer.parseInt(orderId));

        fullname.setText("Name: " + fullnameG);
        address.setText("Address: " + addressG);
        phone.setText("Phone:" + phoneG);

        recyclerView = findViewById(R.id.recycler_view);
        orderDetailAdapter = new OrderDetailAdapter(orderDetails);
        recyclerView.setAdapter(orderDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadOrders();


    }

    private void loadOrders() {
        orderDetailAdapter.setOrderList(orderDetails);
    }
}