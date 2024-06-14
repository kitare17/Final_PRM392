package com.example.myapplication;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;


public class Wishlist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);


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
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);
    }
}