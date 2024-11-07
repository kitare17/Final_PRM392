package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductCartAdapter;
import com.example.myapplication.adapter.RatingAdapter;
import com.example.myapplication.database.CartRepository;
import com.example.myapplication.database.UserInfoRepository;
import com.example.myapplication.model.ProductCart;
import com.example.myapplication.model.Rating;
import com.example.myapplication.model.UserInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CardDetail extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView subTotalValueTextView;
    TextView shippingCostValueTextView;
    TextView totalValueTextView;

    TextView addressTextView;
    TextView phoneAdressTextView;
    ImageView buttonBack;

    AppCompatButton addCardPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_detail);
        recyclerView = findViewById(R.id.product_cart_recycleView);
        subTotalValueTextView = findViewById(R.id.subTotalValueTextView);
        shippingCostValueTextView = findViewById(R.id.shippingCostValueTextView);
        totalValueTextView = findViewById(R.id.totalValueTextView);
        ConstraintLayout constraintLayoutAddress = findViewById(R.id.constraintLayoutAddress);
        addressTextView = findViewById(R.id.addressTextView);
        phoneAdressTextView = findViewById(R.id.phoneAdressTextView);
        addCardPayment = findViewById(R.id.addCardPayment);



        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            finish();
        });
        constraintLayoutAddress.setOnClickListener(v -> {
            Intent intent = new Intent(CardDetail.this, AdressList.class);
            startActivity(intent);
                });

        CartRepository cartRepository=new CartRepository(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "userId");
        UserInfoRepository userInfoRepository = new UserInfoRepository(getApplicationContext());

        UserInfo userInfo = userInfoRepository.getUserById(userId);



        List<ProductCart> itemList = cartRepository.listAll(Integer.parseInt(userInfo.getUserId()));;
        ProductCartAdapter productCartAdapter = new ProductCartAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        subTotalValueTextView.setText(productCartAdapter.totalPrice() + " VNĐ");
        shippingCostValueTextView.setText(productCartAdapter.shippingCost() + " VNĐ");
        totalValueTextView.setText((productCartAdapter.totalPrice() + productCartAdapter.shippingCost()) + " VNĐ");

        productCartAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Toast.makeText(getApplicationContext(), "change! ", Toast.LENGTH_LONG).show();
                subTotalValueTextView.setText(productCartAdapter.totalPrice() + " VNĐ");
                shippingCostValueTextView.setText(productCartAdapter.shippingCost() + " VNĐ");
                totalValueTextView.setText((productCartAdapter.totalPrice() + productCartAdapter.shippingCost()) + " VNĐ");

            }
        });

        recyclerView.setAdapter(productCartAdapter);



        addCardPayment.setOnClickListener(v -> {
            if (itemList.size()>0){
                Intent intent = new Intent(CardDetail.this, OrderConfirmActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Cart is empty",Toast.LENGTH_LONG).show();
            }

        });
    }


    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = getSharedPreferences("Address", Context.MODE_PRIVATE);
        String address = sharedPreferences.getString("address", "Undefine");
        String phone = sharedPreferences.getString("phone", "Undefine");
        addressTextView.setText(address);
        phoneAdressTextView.setText(phone);
        super.onResume();
    }
}