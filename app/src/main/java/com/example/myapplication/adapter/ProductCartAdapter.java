package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.CartRepository;
import com.example.myapplication.model.ProductCart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ViewHolder> {

    List<ProductCart> productCarts;

    public ProductCartAdapter(List<ProductCart> productList) {
        this.productCarts = productList;
    }

    public void setProductList(List<ProductCart> productList) {
        this.productCarts = productList;
    }

    @NonNull
    @Override
    public ProductCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.product_cart_item, parent, false);
        ProductCartAdapter.ViewHolder viewHolder = new ProductCartAdapter.ViewHolder(productView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ProductCartAdapter.ViewHolder holder, int position) {
        ProductCart product = productCarts.get(position);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.valueOf(product.getPrice())+" VNĐ");
        holder.amountTextView.setText(product.getAmount()+"");
        Picasso.get()
                .load(product.getImageUrl())
                .into(holder.imageView);




        holder.increaseImageView.setOnClickListener(v->{
            product.setAmount(product.getAmount()+1);
            CartRepository cartRepository = new CartRepository(v.getContext());
            cartRepository.updateAmount(product.getId(), product.getAmount());
            holder.amountTextView.setText(product.getAmount()+"");
            notifyDataSetChanged();
        });
        holder.decreaseImageView.setOnClickListener(v->{
            if(product.getAmount()>1){
                product.setAmount(product.getAmount()-1);
                CartRepository cartRepository = new CartRepository(v.getContext());
                cartRepository.updateAmount(product.getId(), product.getAmount());
                holder.amountTextView.setText(product.getAmount()+"");
                notifyDataSetChanged();
            }});
        holder.deleteImageView.setOnClickListener(v->{
            CartRepository cartRepository = new CartRepository(v.getContext());
            cartRepository.deleteItem(product.getId());
            productCarts.remove(position);
            notifyDataSetChanged();
        });
    }

    public double totalPrice(){
        double totalPrice = 0;
        for(ProductCart product:productCarts){
            totalPrice += product.getPrice()*product.getAmount();
        }
        return totalPrice;
    }

    public double shippingCost(){
        double shippingCost = 3000;
       return shippingCost;
    }

    @Override
    public int getItemCount() {
        return productCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView imageView;
        public ImageView deleteImageView;
        public ImageView increaseImageView;
        public ImageView decreaseImageView;
        public TextView amountTextView;
        public TextView priceTextView;

        public ViewHolder(View itemView) {

            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.product_cart_name);
            imageView = (ImageView) itemView.findViewById(R.id.product_cart_image);
            priceTextView = (TextView) itemView.findViewById(R.id.product_cart_price);
            deleteImageView = (ImageView) itemView.findViewById(R.id.buttonTrash2);
            increaseImageView = (ImageView) itemView.findViewById(R.id.buttonUp2);
            decreaseImageView = (ImageView) itemView.findViewById(R.id.buttonDown2);
            amountTextView = (TextView) itemView.findViewById(R.id.amountTextView2);
        }
    }
}
