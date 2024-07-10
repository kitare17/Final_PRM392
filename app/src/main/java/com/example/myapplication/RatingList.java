package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.RatingAdapter;
import com.example.myapplication.database.ReviewRepository;
import com.example.myapplication.model.Rating;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RatingList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView amountReview;
    private TextView averageStar;
    private RatingBar averageStarShow;
    private ImageView backButton;

    AppCompatButton addReview;
    private int productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rating_list);


        productId = Integer.parseInt(getIntent().getStringExtra("hack"));
        Toast.makeText(getApplicationContext(),"->"+productId,Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerViewReview);
        amountReview = findViewById(R.id.amountReview);
        averageStar = findViewById(R.id.averageStar);
        backButton = findViewById(R.id.back_button);
        averageStarShow = findViewById(R.id.averageStarShow);
        addReview = findViewById(R.id.addReview);
        List<Rating> shopList=getListShop(productId);
        RatingAdapter ratingAdapter = new RatingAdapter(shopList);
        recyclerView.setAdapter(ratingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addReview.setOnClickListener(v->{
            Intent intent = new Intent(RatingList.this, AddReviewActivity.class);
            intent.putExtra("productId",productId+"");
            startActivity(intent);
                });
        backButton.setOnClickListener(v->{
            finish();
        });

    }

    public List<Rating> getListShop(int productId){

        ReviewRepository reviewRepository=new ReviewRepository(getApplicationContext());
        List<Rating> shopList = reviewRepository.getRatingList(productId);
        amountReview.setText(shopList.size()+" Review");
        double sum=0;
        for (Rating rating:shopList){
            sum+=rating.getRating();
        }
        averageStar.setText(String.format("%.1f",sum/(shopList.size()==0?1:shopList.size()))+"");

        averageStarShow.setRating(Float.parseFloat(sum/(shopList.size()==0?1:shopList.size())+""));
        return shopList;
    }

}