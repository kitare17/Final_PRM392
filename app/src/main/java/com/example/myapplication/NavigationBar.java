package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    Toast.makeText(NavigationBar.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_wishlist) {
                    Intent intent = new Intent(NavigationBar.this, Wishlist.class);
                    startActivity(intent);
                    return true;
                }
                else if (itemId == R.id.action_cart) {
                    Toast.makeText(NavigationBar.this, "Cart", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.action_wallet) {
                    Toast.makeText(NavigationBar.this, "Wallet", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

}


