package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.databinding.ActivityShowItemBinding;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ShowItemActivity extends AppCompatActivity {

    private ActivityShowItemBinding binding;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the binding
        binding = ActivityShowItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.productRecyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize the product list and add products
        productList = new ArrayList<>();
        productList.add(new Product(1,"Nike Sportswear Club", "Fleece", 99.9, R.drawable.product_example));
        productList.add(new Product(2,"Nike Sportswear Club", "Fleece", 99.9, R.drawable.product_example));
        productList.add(new Product(3,"Nike Sportswear Club", "Fleece", 99.9, R.drawable.product_example));
        productList.add(new Product(4,"Nike Sportswear Club", "Fleece", 99.9, R.drawable.product_example));
        productList.add(new Product());


        // Initialize the adapter with the current context and product list
        productAdapter = new ProductAdapter(productList, getLayoutInflater());
        recyclerView.setAdapter(productAdapter);

        // Set Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
