package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
public class ChatAuth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_auth);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
       String googleId = sharedPreferences.getString("googleId", "1");

        if(googleId.equals("cxcq3OdM7EQ2aNyhq6RCYlwkh4y1")){
            Intent intent = new Intent(this, CustomerListActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            // Navigate to OptionRole activity or any other activity as per your logic
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
            finish();
        }
    }
}