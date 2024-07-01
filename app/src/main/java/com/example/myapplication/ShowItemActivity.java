package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductTestAdapter;
import com.example.myapplication.database.ProductRepository;
import com.example.myapplication.databinding.ActivityShowItemBinding;
import com.example.myapplication.model.ProductTest;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowItemActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ActivityShowItemBinding binding;
    private RecyclerView recyclerView;
    private ProductTestAdapter productAdapter;
    private List<ProductTest> productList;
    private ImageView backButton;
    private SearchView searchView;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the binding
        binding = ActivityShowItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.productRecyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Get all products
        getAllProduct();

        // Initialize the adapter with the current context and product list
        productAdapter = new ProductTestAdapter(getApplicationContext(), productList, getLayoutInflater());
        recyclerView.setAdapter(productAdapter);

        // Set Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            finish();
        });
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            public boolean onQueryTextChange(final String query) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findProduct(query);
                    }
                }, 400);
                return false;
            }
        });




        TextView btn = (TextView) findViewById(R.id.sort);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ShowItemActivity.this, v);
                popup.setOnMenuItemClickListener(ShowItemActivity.this);
                popup.inflate(R.menu.menu_sort);
                popup.show();
            }
        });

    }

    private void getAllProduct() {
        ProductRepository productRepository = new ProductRepository(this);
        productList = productRepository.getAllProduct();

    }

    private void findProduct(String keyword) {
        ProductRepository productRepository = new ProductRepository(this);
        productList = productRepository.searchProduct(keyword);
        productAdapter = new ProductTestAdapter(this.getBaseContext(), productList, getLayoutInflater());
        recyclerView.setAdapter(productAdapter);
    }

//    public void showPopup(View v) {
//        PopupMenu popup = new PopupMenu(getApplicationContext(), v);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_sort, popup.getMenu());
//        popup.show();
//    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (item.getItemId() == R.id.priceIn) {
            Toast.makeText(this, "Price (Ascending)", Toast.LENGTH_SHORT).show();

            Collections.sort(productList, new Comparator<ProductTest>(){
                public int compare(ProductTest obj1, ProductTest obj2) {
                    // ## Ascending order
                     return Double.valueOf(obj1.getPrice()).compareTo(Double.valueOf(obj2.getPrice()));
                }

            });
            productAdapter = new ProductTestAdapter(this.getBaseContext(), productList, getLayoutInflater());
            recyclerView.setAdapter(productAdapter);
            return true;
        } else if (item.getItemId() == R.id.priceDe) {
            Toast.makeText(this, "Price (Ascending)", Toast.LENGTH_SHORT).show();

            Collections.sort(productList, new Comparator<ProductTest>(){
                public int compare(ProductTest obj1, ProductTest obj2) {
                    // ## Ascending order
                    return Double.valueOf(obj2.getPrice()).compareTo(Double.valueOf(obj1.getPrice()));
                }

            });
            productAdapter = new ProductTestAdapter(this.getBaseContext(), productList, getLayoutInflater());
            recyclerView.setAdapter(productAdapter);
            return true;
        }



        return true;
    }


}
