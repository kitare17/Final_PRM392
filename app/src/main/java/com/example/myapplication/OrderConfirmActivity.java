package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.database.CartRepository;
import com.example.myapplication.database.UserInfoRepository;
import com.example.myapplication.model.ProductCart;
import com.example.myapplication.model.UserInfo;

import java.util.List;

public class OrderConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_confirm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String googleId = sharedPreferences.getString("googleId", "googleId");
        UserInfoRepository userInfoRepository = new UserInfoRepository(getApplicationContext());
        UserInfo userInfo = userInfoRepository.getUserByIdGoogle(googleId);
        CartRepository cartRepository=new CartRepository(getApplicationContext());


        List<ProductCart> itemList = cartRepository.listAll(Integer.parseInt(userInfo.getUserId()));
        SharedPreferences sharedPreferences1 = getSharedPreferences("Address", Context.MODE_PRIVATE);
        String address = sharedPreferences1.getString("address", "Undefine");
        String phone = sharedPreferences1.getString("phone", "Undefine");

        cartRepository.makeOrder(Integer.parseInt(userInfo.getUserId()), address, phone,itemList);



    }

    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Back button pressed", Toast.LENGTH_SHORT).show();
        Intent intent =new Intent(OrderConfirmActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}