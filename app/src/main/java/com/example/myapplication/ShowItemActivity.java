package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductTestAdapter;
import com.example.myapplication.database.ProductRepository;
import com.example.myapplication.databinding.ActivityShowItemBinding;
import com.example.myapplication.model.ProductTest;

import java.util.List;

public class ShowItemActivity extends AppCompatActivity {

    private ActivityShowItemBinding binding;
    private RecyclerView recyclerView;
    private ProductTestAdapter productAdapter;
    private List<ProductTest> productList;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the binding
        binding = ActivityShowItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.productRecyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Get all products
        getAllProduct();

        // Initialize the adapter with the current context and product list
        productAdapter = new ProductTestAdapter(getApplicationContext(),productList, getLayoutInflater());
        recyclerView.setAdapter(productAdapter);

        // Set Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(v->{
            finish();
        });
    }

    private void getAllProduct(){
        ProductRepository productRepository = new ProductRepository(this);
        productList = productRepository.getAllProduct();

    }
}
