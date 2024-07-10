package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageButton;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.database.CartRepository;
import com.example.myapplication.database.ProductRepository;
import com.example.myapplication.database.ReviewRepository;
import com.example.myapplication.database.UserInfoRepository;
import com.example.myapplication.databinding.ActivityProductDetailBinding;
import com.example.myapplication.databinding.ActivityShowItemBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductTest;
import com.example.myapplication.model.Rating;
import com.example.myapplication.model.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private ActivityProductDetailBinding binding;

    private  TextView productName;
    private  TextView productPrice;
    private  TextView viewMoreReview;
    private  TextView noReview;
    private ImageView productImageView;
    private ImageButton backButton;
    private ImageButton cartButton;
    private AppCompatButton addToCartButton;
    private ImageButton addWishListButton;


    public TextView timeText;
    public TextView fullnameText;
    public TextView countStar;

    public RatingBar ratingStar;
    public TextView detailText;

    public ImageView avatarReview;

    private LinearLayoutManager firstReview;
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


//        // Initialize the RecyclerView
//        RecyclerView recyclerView = binding.recyclerView;
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Using a LinearLayoutManager with horizontal orientation
        ProductRepository productRepository = new ProductRepository(getApplicationContext());
        ProductTest product = productRepository.getProductById(Integer.parseInt(productId));

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String googleId = sharedPreferences.getString("googleId", "googleId");

        UserInfoRepository userInfoRepository = new UserInfoRepository(getApplicationContext());
        UserInfo userInfo = userInfoRepository.getUserByIdGoogle(googleId);




        productName=findViewById(R.id.productName);
//        firstReview=findViewById(R.id.firstReview);
        productPrice=findViewById(R.id.productPrice);
        productImageView=findViewById(R.id.product_image);;
        backButton=findViewById(R.id.back_button);
        cartButton=findViewById(R.id.cartButton);
        addToCartButton=findViewById(R.id.add_to_cart_button);
        viewMoreReview=findViewById(R.id.viewMoreReview);
        noReview=findViewById(R.id.noReview);
        addWishListButton=findViewById(R.id.addWishListButton);





        //add wishlist
        addWishListButton.setOnClickListener(v->{
            Toast.makeText(getApplicationContext(),"Add to wishlist",Toast.LENGTH_SHORT).show();
        });


        //first review
        timeText = (TextView) findViewById(R.id.timeText);
        fullnameText = (TextView) findViewById(R.id.fullnameText);
        detailText = (TextView) findViewById(R.id.detailText);
        countStar = (TextView) findViewById(R.id.countStar);
        ratingStar = (RatingBar) findViewById(R.id.ratingStar);
        avatarReview = (ImageView) findViewById(R.id.avatarReview1);



        ReviewRepository reviewRepository=new ReviewRepository(getApplicationContext());
        Rating firstRating=reviewRepository.getFirstRating(Integer.parseInt(productId));
        if(firstRating==null){
            findViewById(R.id.included_layout).setVisibility(View.GONE);
            viewMoreReview.setVisibility(View.GONE);
        }
        else{
            timeText.setText(firstRating.getRatingDate().getMonth() + ", " + firstRating.getRatingDate().getYear());
            fullnameText.setText(firstRating.getFullname());
            detailText.setText(firstRating.getDetail());
            countStar.setText(firstRating.getRating().toString());
            ratingStar.setRating(firstRating.getRating().floatValue());
            Picasso.get().load(firstRating.getImageUrl()).into(avatarReview);
            noReview.setVisibility(View.GONE);
        }


        viewMoreReview.setOnClickListener(v->{
            Intent intent = new Intent(ProductDetailActivity.this, RatingList.class);
            Toast.makeText(getApplicationContext(),productId,Toast.LENGTH_SHORT).show();
            intent.putExtra("hack", productId);
            startActivity(intent);
        });

        addToCartButton.setOnClickListener(v->{

            CartRepository cartRepository = new CartRepository(getApplicationContext());
            cartRepository.addToCart(product.getId()+"",1,Integer.parseInt(userInfo.getUserId()));

        });
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

    }


}