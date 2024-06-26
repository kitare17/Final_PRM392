package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductCartAdapter;
import com.example.myapplication.adapter.RatingAdapter;
import com.example.myapplication.model.ProductCart;
import com.example.myapplication.model.Rating;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CardDetail extends AppCompatActivity  {
     RecyclerView recyclerView;
     TextView subTotalValueTextView;
     TextView shippingCostValueTextView;
     TextView totalValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_detail);
        recyclerView = findViewById(R.id.product_cart_recycleView);
        subTotalValueTextView = findViewById(R.id.subTotalValueTextView);
        shippingCostValueTextView = findViewById(R.id.shippingCostValueTextView);
        totalValueTextView = findViewById(R.id.totalValueTextView);



        List<ProductCart> itemList=getListItem();
        ProductCartAdapter productCartAdapter = new ProductCartAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        subTotalValueTextView.setText(productCartAdapter.totalPrice()+"");
        shippingCostValueTextView.setText(productCartAdapter.shippingCost()+"");
        totalValueTextView.setText((productCartAdapter.totalPrice()+productCartAdapter.shippingCost())+"");

        productCartAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Toast.makeText(getApplicationContext(), "change! ", Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(productCartAdapter);
    }

    public List<ProductCart> getListItem(){

        List<ProductCart> itemList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
             int id = i;
             String name = "Product name" + i;
             String type = "Product";
             double price = i*1000;
             int amount = 1;
             String imageUrl= "";
            ProductCart productCart = new ProductCart( id,  name,  type,  price,  amount, imageUrl);
            itemList.add(productCart);
        }
        return itemList;
    }
}