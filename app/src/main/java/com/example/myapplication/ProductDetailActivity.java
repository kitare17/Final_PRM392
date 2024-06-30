package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.database.ProductRepository;
import com.example.myapplication.databinding.ActivityProductDetailBinding;
import com.example.myapplication.databinding.ActivityShowItemBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductTest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private ActivityProductDetailBinding binding;

    private  TextView productName;
    private  TextView productPrice;
    private ImageView productImageView;
    private ImageButton backButton;
    private ImageButton cartButton;
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



        Intent intentView = getIntent();
        String productId = intentView.getStringExtra("productId");

        Toast.makeText(getApplicationContext(), productId+" ok", Toast.LENGTH_SHORT).show();
//        // Initialize the RecyclerView
//        RecyclerView recyclerView = binding.recyclerView;
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Using a LinearLayoutManager with horizontal orientation
        ProductRepository productRepository = new ProductRepository(getApplicationContext());
        ProductTest product = productRepository.getProductById(Integer.parseInt(productId));


        productName=findViewById(R.id.productName);
        productPrice=findViewById(R.id.productPrice);
        productImageView=findViewById(R.id.product_image);;
        backButton=findViewById(R.id.back_button);
        cartButton=findViewById(R.id.cartButton);
        cartButton.setOnClickListener(v->{
            Intent intent = new Intent(this, CardDetail.class);
            startActivity(intent);
        });
        backButton.setOnClickListener(v->{
            finish();
        });
        Picasso.get()
                .load(product.getImageUrl())
                .into(productImageView);


        productName.setText(product.getName());
        productPrice.setText(String.valueOf(product.formatVND()));


        // set read more start
        TextView descriptionTextView = findViewById(R.id.description_text);
        String description = product.getProductDetail();


        SpannableString spannableString = new SpannableString(description );

        // Set the color and size for "Read more"
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), description.length(), spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.125f), description.length(), spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        descriptionTextView.setText(spannableString);
        // set read more end
        // Set the adapter
//        ImageAdapter imageAdapter = new ImageAdapter(this, productList);
//        recyclerView.setAdapter(imageAdapter);
    }


}