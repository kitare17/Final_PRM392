package com.example.myapplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("DeskDelights Shop");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_slide_bar, R.string.close_slide_bar);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //set session user
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String fullname = sharedPreferences.getString("fullname", "user");



        View headerView = navigationView.getHeaderView(0); // 0-indexed

// Find the TextView inside the header layout
        TextView headerTextView = headerView.findViewById(R.id.fullnameSlideBar);
        headerTextView.setText(fullname);





//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Setting()).commit();
//            navigationView.setCheckedItem(R.id.nav_settings);
//        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.nav_home) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
//        } else if (item.getItemId() == R.id.nav_settings) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Setting()).commit();
//        } else if (item.getItemId() == R.id.nav_logout) {
//            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Setting()).commit();
//        } else {
//            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
//        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}