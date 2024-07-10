package com.example.myapplication.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Rating;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;


import java.util.List;

public class RatingAdapter extends
        RecyclerView.Adapter<RatingAdapter.ViewHolder> {


    private List<Rating> ratingList;

    // Pass in the contact array into the constructor
    public RatingAdapter(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.review_comment_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Rating contact = ratingList.get(position);


        TextView timeText = holder.timeText;
        TextView fullnameText = holder.fullnameText;
        TextView countStar = holder.countStar;
        RatingBar ratingStar = holder.ratingStar;
        TextView detailText = holder.detailText;
        ImageView avatarReview = holder.avatarReview;
        timeText.setText(contact.getRatingDate().getMonth() + ", " + contact.getRatingDate().getYear());
        fullnameText.setText(contact.getFullname());
        countStar.setText(contact.getRating().toString());
        ratingStar.setRating(contact.getRating().floatValue());
        detailText.setText(contact.getDetail());
        Picasso.get().load(contact.getImageUrl()).into(avatarReview);
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView timeText;
        public TextView fullnameText;
        public TextView countStar;

        public RatingBar ratingStar;
        public TextView detailText;

        public ImageView avatarReview;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            timeText = (TextView) itemView.findViewById(R.id.timeText);
            fullnameText = (TextView) itemView.findViewById(R.id.fullnameText);
            detailText = (TextView) itemView.findViewById(R.id.detailText);
            countStar = (TextView) itemView.findViewById(R.id.countStar);
            ratingStar = (RatingBar) itemView.findViewById(R.id.ratingStar);
            avatarReview = (ImageView) itemView.findViewById(R.id.avatarReview1);

        }
    }
}

