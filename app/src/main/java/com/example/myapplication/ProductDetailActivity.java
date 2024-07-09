package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.adapter.ProductTestAdapter;
import com.example.myapplication.database.ProductRepository;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductTest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private Button b1;
    private ImageView iv, ivBack, productImage;
    private TextView tvPName, tvPType, tvPPrice, tvPDetail;
    private ProductRepository productRepository;
    private List<Product> wishList;
    private static boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        initView();
        productRepository = new ProductRepository(this);
        Product product = new Product();
        wishList = productRepository.getProductInWishList();

        ivBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        if (intent.hasExtra("product_id")) {
            int productId = intent.getIntExtra("product_id", -1);
            for (Product p : wishList) {
                if (p.getId() == productId) {
                    iv.setImageResource(R.drawable.ic_close);
                    check = true;
                    break;
                }
            }
            product = productRepository.getProduct(productId);
            Picasso.get()
                    .load(product.getImageUrl())
                    .into(productImage);
            tvPName.setText(product.getName());
            tvPType.setText(product.getType());
            tvPPrice.setText(String.format("%.0f", product.getPrice())+" VND");
            tvPDetail.setText(product.getProductDetail());
            if (check) {
                iv.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Are you sure you want to delete this item from wish list?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Delete the wishlist item
                                    productRepository.deleteWish(productId);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog, do nothing
                                    dialog.dismiss();
                                }
                            });
                    // Create and show the dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                });
            } else {
                product.setFavorite(true);
                Product finalProduct = product;
                iv.setOnClickListener(v -> {
                    productRepository.insertWishList(finalProduct);
                });
            }

        } else {

        }
    }

    private void initView() {
        b1 = findViewById(R.id.add_to_cart);
        iv = findViewById(R.id.ic_heart);
        ivBack = findViewById(R.id.ic_back);
        productImage = findViewById(R.id.productImage);
        tvPName = findViewById(R.id.productName);
        tvPType = findViewById(R.id.productType);
        tvPPrice = findViewById(R.id.productPrice);
        tvPDetail = findViewById(R.id.productDetail);
    }

}