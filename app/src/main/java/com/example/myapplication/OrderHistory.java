package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
        orderList = orderRepository.getOrders();

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
