package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.BrandAdapter;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.databinding.ActivityHomeBinding;
import com.example.myapplication.model.Brand;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private RecyclerView brandsRecyclerView;
    private BrandAdapter brandAdapter;
    private List<Brand> brandsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the binding and set the content view to the root of the binding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize data
        initializeData();

        // Setup RecyclerViews for products and brands
        setupRecyclerViews();

        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            int left = insets.getInsets(WindowInsetsCompat.Type.systemBars()).left;
            int top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            int right = insets.getInsets(WindowInsetsCompat.Type.systemBars()).right;
            int bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            v.setPadding(left, top, right, bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void initializeData() {
        // Initialize the brand list and add brands
        brandsList = new ArrayList<>();
        brandsList.add(new Brand(1, R.drawable.adidas_brand, "Adidas"));
        brandsList.add(new Brand(1, R.drawable.adidas_brand, "Adidas"));
        brandsList.add(new Brand(1, R.drawable.adidas_brand, "Adidas"));
        brandsList.add(new Brand(1, R.drawable.adidas_brand, "Adidas"));

        // Initialize the product list and populate with products
        productList = new ArrayList<>();
        productList.add(new Product(1, "Nike Sportswear Club", "Fleece", 99.9, R.drawable.product_example));
        productList.add(new Product(1, "Nike Sportswear Club", "Fleece", 99.9, R.drawable.product_example));
        productList.add(new Product(1, "Nike Sportswear Club", "Fleece", 99.9, R.drawable.product_example));
        productList.add(new Product(1, "Nike Sportswear Club", "Fleece", 99.9, R.drawable.product_example));
        productList.add(new Product(1, "Nike Sportswear Club", "Fleece", 99.9, R.drawable.product_example));
    }

    private void setupRecyclerViews() {
        // Setup RecyclerView for brands
        brandsRecyclerView = binding.brandsRecyclerView;
        brandsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        brandAdapter = new BrandAdapter(brandsList);
        brandsRecyclerView.setAdapter(brandAdapter);

        // Setup RecyclerView for products
        productsRecyclerView = binding.productRecyclerView;
        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter = new ProductAdapter(productList, getLayoutInflater());
        productsRecyclerView.setAdapter(productAdapter);
    }
}
