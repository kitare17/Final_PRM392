package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.database.ProductRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ProductRepository productRepository = new ProductRepository(getApplicationContext());
        productRepository.getAllProduct();
        Button button = findViewById(R.id.buttonShow);
        button.setOnClickListener(v -> {

        });
    }
}