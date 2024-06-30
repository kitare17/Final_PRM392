package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Brand;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private List<Brand> brands;

    public BrandAdapter(List<Brand> brands) {
        this.brands = brands;
    }

    @Override
    public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandViewHolder holder, int position) {
        Brand brand = brands.get(position);
        Picasso.get().load(brand.getLogoResId()).into(holder.brandLogo);
        holder.brandName.setText(brand.getName());
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    static class BrandViewHolder extends RecyclerView.ViewHolder {
        ImageView brandLogo;
        TextView brandName;

        BrandViewHolder(View itemView) {
            super(itemView);
            brandLogo = itemView.findViewById(R.id.brandLogo);
            brandName = itemView.findViewById(R.id.brandName);
        }
    }
}