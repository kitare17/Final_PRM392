package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
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

public class HomeActivity extends Fragment {
    private ActivityHomeBinding binding;
    private RecyclerView productsRecyclerView;
    private ProductTestAdapter ProductTestAdapter;
    private List<ProductTest> productList;
    private RecyclerView brandsRecyclerView;
    private BrandAdapter brandAdapter;
    private List<Brand> brandsList;
    private ProductRepository productRepository;

    private TextView viewAllProduct;

    private AppCompatButton cart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cart= (AppCompatButton) getView().findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CardDetail.class);
                startActivity(intent);
            }
        });

        // Initialize the ProductRepository
        productRepository = new ProductRepository(getContext());

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

        viewAllProduct = (TextView) getView().findViewById(R.id.viewAllProduct);

        viewAllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowItemActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initializeData() {

        // Get all products from the repository
        productList = productRepository.getAllProduct();
        brandsList = productRepository.getAllBrands();
    }

    private void setupRecyclerViews() {
        // Setup RecyclerView for brands
        brandsRecyclerView = binding.brandsRecyclerView;
        brandsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        brandAdapter = new BrandAdapter(brandsList);
        brandsRecyclerView.setAdapter(brandAdapter);

        // Setup RecyclerView for products
        productsRecyclerView = binding.productRecyclerView;
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ProductTestAdapter = new ProductTestAdapter(getContext(),productList, getLayoutInflater());
        productsRecyclerView.setAdapter(ProductTestAdapter);
    }

    @Override
    public void onDestroy() {
        productRepository.close();
        super.onDestroy();
    }
}
