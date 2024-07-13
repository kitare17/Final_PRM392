package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.adapter.ProductTestAdapter;
import com.example.myapplication.adapter.WishListAdapter;
import com.example.myapplication.database.ProductRepository;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductTest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class Wishlist extends AppCompatActivity implements WishListAdapter.OnItemClickListener {
    private BottomNavigationView navigationView;
    private RecyclerView recyclerView;
    private WishListAdapter adapter;
    private List<Product> productList;
    private ProductRepository productRepository;
    private ImageView imageView;
    private Button button;
    private static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

//        navigationView.setSelectedItemId(R.id.action_wishlist);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.edit_button);

        imageView.setOnClickListener(v -> onBackPressed());

        productRepository = new ProductRepository(Wishlist.this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "1");
        productList = productRepository.getProductInWishList(Integer.parseInt(userId));



        recyclerView = findViewById(R.id.rvProducts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new WishListAdapter(getApplicationContext(),productList, productRepository);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    adapter.setEditMode(true);
                    flag = true;
                } else {
                    adapter.setEditMode(false);
                    flag = false;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update the selected item to Wishlist
//        navigationView.setSelectedItemId(R.id.action_wishlist);
    }

    @Override
    public void onItemClick(Product product) {
        Intent intent = new Intent(Wishlist.this, ProductDetailActivity.class);
        intent.putExtra("product_id", product.getId());
        startActivity(intent);
    }
}