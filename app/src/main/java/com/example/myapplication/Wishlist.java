package com.example.myapplication;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.adapter.WishListAdapter;
import com.example.myapplication.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class Wishlist extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private RecyclerView recyclerView;
    private WishListAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setSelectedItemId(R.id.action_wishlist);


        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.img, "Nike Sportswear Club Fleece", 99));
        productList.add(new Product(R.drawable.img, "Trail Running Jacket Nike Windrunner", 99));
        productList.add(new Product(R.drawable.img, "Trail Running Jacket Nike Windrunner", 99));
        productList.add(new Product(R.drawable.img, "Nike Sportswear Club Fleece", 99));
        productList.add(new Product(R.drawable.img, "Nike Sportswear Club Fleece", 99));
        productList.add(new Product(R.drawable.img, "Nike Sportswear Club Fleece", 99));
        productList.add(new Product(R.drawable.img, "Nike Sportswear Club Fleece", 99));


        recyclerView = findViewById(R.id.rvProducts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new WishListAdapter(productList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Update the selected item to Wishlist
        navigationView.setSelectedItemId(R.id.action_wishlist);
    }
}