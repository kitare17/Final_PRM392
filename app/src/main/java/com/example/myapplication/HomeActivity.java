package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.BrandAdapter;
import com.example.myapplication.adapter.ProductTestAdapter;
import com.example.myapplication.database.ProductRepository;
import com.example.myapplication.databinding.ActivityHomeBinding;
import com.example.myapplication.model.Brand;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductTest;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private RecyclerView productsRecyclerView;
    private ProductTestAdapter ProductTestAdapter;
    private List<ProductTest> productList;
    private RecyclerView brandsRecyclerView;
    private BrandAdapter brandAdapter;
    private List<Brand> brandsList;
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the binding and set the content view to the root of the binding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the ProductRepository
        productRepository = new ProductRepository(this);

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
//        brandsList = new ArrayList<>();
//        brandsList.add(new Brand(1, R.drawable.adidas_brand, "Adidas"));
//        brandsList.add(new Brand(2, R.drawable.adidas_brand, "Nike"));
//        brandsList.add(new Brand(3, R.drawable.adidas_brand, "Puma"));
//        brandsList.add(new Brand(4, R.drawable.adidas_brand, "Reebok"));

        // Get all products from the repository
        productList = productRepository.getAllProduct();
        brandsList = productRepository.getAllBrands();
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
        ProductTestAdapter = new ProductTestAdapter(productList, getLayoutInflater());
        productsRecyclerView.setAdapter(ProductTestAdapter);
    }

    @Override
    protected void onDestroy() {
        productRepository.close();
        super.onDestroy();
    }
}
