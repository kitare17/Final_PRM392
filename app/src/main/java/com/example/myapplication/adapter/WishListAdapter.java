package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.ProductRepository;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductTest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    List<Product> productList;
    private boolean isEditMode = false;
    private ProductRepository productRepository;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public WishListAdapter(List<Product> productList, ProductRepository productRepository) {
        this.productList = productList;
        this.productRepository = productRepository;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.activity_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(productView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.valueOf(product.getPrice()));
        Picasso.get()
                .load(product.getImageUrl())
                .into(holder.imageView);
        holder.btnRemove.setVisibility(isEditMode ? View.VISIBLE : View.INVISIBLE);
        holder.btnRemove.setOnClickListener(v -> {
            productRepository.deleteWish(product.getId());
            productList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productList.size());
        });
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView imageView;
        public TextView priceTextView;
        public Button btnRemove;

        public ViewHolder(View itemView) {

            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.product_name);
            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            priceTextView = (TextView) itemView.findViewById(R.id.product_price);
            btnRemove = itemView.findViewById(R.id.btn_remove);

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
        }

        public void bind(Product product) {
            nameTextView.setText(product.getName());
            priceTextView.setText(String.valueOf(product.getPrice()));
        }
    }
}
