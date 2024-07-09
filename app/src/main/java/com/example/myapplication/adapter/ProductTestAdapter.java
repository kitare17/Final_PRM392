package com.example.myapplication.adapter;



import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductTest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductTestAdapter extends RecyclerView.Adapter<ProductTestAdapter.ViewHolder> {

    private List<ProductTest> productList;
    private final LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private OnHeartClickListener onHeartClickListener;

    public interface OnItemClickListener {
        void onItemClick(ProductTest product);
    }

    public interface OnHeartClickListener {
        void onHeartClick(ProductTest product, boolean isFavorite);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnHeartClickListener(OnHeartClickListener onHeartClickListener) {
        this.onHeartClickListener = onHeartClickListener;
    }

    public ProductTestAdapter(List<ProductTest> productList, LayoutInflater inflater) {
        this.productList = productList;
        this.inflater = inflater;
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
            holder.productName.setText(product.getName());
            holder.productType.setText(product.getType());
            holder.productPrice.setText( String.format("%.0f", product.getPrice())+" VND");
            // Use Picasso to load image from URL
            Picasso.get()
                    .load(product.getImageUrl())
                    .into(holder.productImage);
        }
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, heartIcon;
        TextView productName;
        TextView productPrice;
        TextView productType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productType = itemView.findViewById(R.id.productType);
            heartIcon = itemView.findViewById(R.id.ic_heart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(productList.get(position));
                        }
                    }
                }
            });

            heartIcon.setOnClickListener(v -> {
                if (onHeartClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ProductTest product = productList.get(position);
                        boolean isFavorite = product.isFavorite();
                        onHeartClickListener.onHeartClick(product, !isFavorite);
                        product.setFavorite(!isFavorite);
                        notifyItemChanged(position);
                    }
                }
            });
        }

        public void bind(ProductTest product) {
            productName.setText(product.getName());
            productPrice.setText(String.valueOf(product.getPrice()));
            heartIcon.setColorFilter(product.isFavorite() ? Color.RED : Color.GRAY);
        }
    }
}
