package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.ProductDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.ProductTest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductTestAdapter extends RecyclerView.Adapter<ProductTestAdapter.ViewHolder> {

    private List<ProductTest> productList;
    private final LayoutInflater inflater;
    private Context context;


    public ProductTestAdapter(Context context,List<ProductTest> productList, LayoutInflater inflater) {
        this.productList = productList;
        this.inflater = inflater;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductTest product = productList.get(position);
        if (product != null) {
            holder.productName.setText(
                    product.getName()
            );
            holder.productType.setText(product.getType());
            holder.productPrice.setText( product.formatVND());
            // Use Picasso to load image from URL
            Picasso.get()
                    .load(product.getImageUrl())
                    .into(holder.productImage);

            holder.productImage.setOnClickListener(v->{


                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("productId", product.getId()+"");
                context.startActivity(intent);

            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productType = itemView.findViewById(R.id.productType);
        }
    }
}
