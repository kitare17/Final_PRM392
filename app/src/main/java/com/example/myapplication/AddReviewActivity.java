package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.database.ReviewRepository;

public class AddReviewActivity extends AppCompatActivity {

    EditText experienceEditText;
    SeekBar ratingSeekBar;
    AppCompatButton submitReviewButton;
    private int productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_review);
        experienceEditText = findViewById(R.id.experienceEditText);
        ratingSeekBar = findViewById(R.id.ratingSeekBar);
        submitReviewButton = findViewById(R.id.submitReviewButton);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "1");


        productId = Integer.parseInt(getIntent().getStringExtra("productId"));
        Toast.makeText(getApplicationContext(),"->"+productId,Toast.LENGTH_SHORT).show();

        submitReviewButton.setOnClickListener(
                v -> {
                    String detail = experienceEditText.getText().toString();
                    int star = ratingSeekBar.getProgress();
                    int productId = 1;
                    ReviewRepository reviewRepository = new ReviewRepository(getApplicationContext());
                    reviewRepository.createReview(productId, Integer.parseInt(userId), star, detail);
                    finish();
                }
        );

    }
}