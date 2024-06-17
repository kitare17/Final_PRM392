package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.database.ProductRepository;
import com.example.myapplication.database.UserInfoRepository;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ProductRepository productRepository = new ProductRepository(getApplicationContext());
        productRepository.getAllProduct();
        Button button = findViewById(R.id.buttonShow);


        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        String fullname= sharedPreferences.getString("fullname", "");
        String email= sharedPreferences.getString("email", "");
        String avatar= sharedPreferences.getString("avatar", "");
        String googleId=sharedPreferences.getString("googleId", "");

        textView = findViewById(R.id.fullnameText);
        textView.setText("Welcome, " + fullname + "!");
        button.setOnClickListener(v -> {


        });
    }
}