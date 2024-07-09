package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavbarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_navigation_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navigationView = view.findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    Toast.makeText(getActivity(), "Home", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_wishlist) {
                    Intent intent = new Intent(getActivity(), Wishlist.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.action_cart) {
                    Toast.makeText(getActivity(), "Cart", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_wallet) {
                    Toast.makeText(getActivity(), "Wallet", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}