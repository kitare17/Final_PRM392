package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ImageAdapter;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.databinding.ActivityProductDetailBinding;
import com.example.myapplication.databinding.ActivityShowItemBinding;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private ActivityProductDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the binding
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        // Initialize the RecyclerView
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Using a LinearLayoutManager with horizontal orientation

        // Create a list of products
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Product 1", "Type 1", 10.0, R.drawable.p_detail));
        productList.add(new Product(2, "Product 2", "Type 2", 20.0, R.drawable.p_detail));
        productList.add(new Product(2, "Product 2", "Type 2", 20.0, R.drawable.p_detail));
        productList.add(new Product(2, "Product 2", "Type 2", 20.0, R.drawable.p_detail));
        productList.add(new Product(2, "Product 2", "Type 2", 20.0, R.drawable.p_detail));
        productList.add(new Product(2, "Product 2", "Type 2", 20.0, R.drawable.p_detail));
        productList.add(new Product(2, "Product 2", "Type 2", 20.0, R.drawable.p_detail));
        // Add more products as needed

        // set read more start
        TextView descriptionTextView = findViewById(R.id.description_text);
        String description = getString(R.string.description_text);
        String readMore = " Read more..";

        SpannableString spannableString = new SpannableString(description + readMore);

        // Set the color and size for "Read more"
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), description.length(), spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.125f), description.length(), spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        descriptionTextView.setText(spannableString);
        // set read more end
        // Set the adapter
        ImageAdapter imageAdapter = new ImageAdapter(this, productList);
        recyclerView.setAdapter(imageAdapter);
    }
}