package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.model.UserInfo;
import com.example.myapplication.view_model.UserViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;

    private ShapeableImageView avatarUser;
    UserViewModel userViewModel;
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
        String avatar = sharedPreferences.getString("avatar", "avatar");




        View headerView = navigationView.getHeaderView(0); // 0-indexed

// Find the TextView inside the header layout
        TextView headerTextView = headerView.findViewById(R.id.fullnameSlideBar);
        ShapeableImageView avatarUser = headerView.findViewById(R.id.avatarUser);
        Picasso.get()
                .load(avatar)
                .into(avatarUser);
        headerTextView.setText(fullname);



// set delfaut view
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeActivity()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserInfo().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo user) {
                if (user != null) {
                    headerTextView.setText(user.getFullname());
                }
            }
        });


        //auth
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeActivity()).commit();
        } else if (item.getItemId() == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Profile()).commit();
        }
        else if (item.getItemId() == R.id.nav_logout) {
            signOut();
            Intent intent = new Intent(MainActivity.this, GetStarted.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.nav_maps) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
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
    public UserViewModel getUserViewModel() {
        return userViewModel;
    }
    private void signOut() {
        auth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Toast.makeText(getApplicationContext(), "Signed Out", Toast.LENGTH_SHORT).show();
        });
        SharedPreferences settings = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        finish();
    }

}