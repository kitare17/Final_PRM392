package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.RatingAdapter;
import com.example.myapplication.model.Rating;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RatingList extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rating_list);
        recyclerView = findViewById(R.id.recyclerViewReview);
        List<Rating> shopList=getListShop();
        RatingAdapter ratingAdapter = new RatingAdapter(shopList);
        recyclerView.setAdapter(ratingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public List<Rating> getListShop(){

        List<Rating> shopList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String fullname = "Fullname " + i;
            Double ratingValue = 4.0;
            LocalDateTime ratingDate = LocalDateTime.now();
            String detail = "This product is a game-changer! Its sleek design and seamless functionality make it a must-have. Versatile and reliable, it caters to all my needs effortlessly. Plus, the customer service is exceptional. Highly recommend!" + i;
            int image =R.drawable.pug;
            Rating rating = new Rating(fullname, ratingValue, ratingDate, detail, image);
            shopList.add(rating);
        }
        return shopList;
    }

}