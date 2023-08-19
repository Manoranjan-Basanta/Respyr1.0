package com.humorstech.respyr.daily_routine.added;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.humorstech.respyr.R;

import java.util.List;



public class AddedFoodAdapter2 extends RecyclerView.Adapter<AddedFoodAdapter2.ViewHolder> {
    private List<AddedFoodData> foodItems;
    private Context context;

    public AddedFoodAdapter2(List<AddedFoodData> foodItems, Context context) {
        this.foodItems = foodItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.added_meals, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddedFoodData foodItem = foodItems.get(position);
        holder.foodNameTextView.setText(foodItem.getFoodName());
        holder.foodQuantityTextView.setText(foodItem.getFoodQuantity()+" grams");

        String actualImage = "https://humorstech.com/humors_app/app_final/"+foodItem.getImageLink1();

        Glide.with(context)
                .load(actualImage)
                .placeholder(R.drawable.food_image_not_found)
                .into(holder.imageFoodImage);



    }


    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodNameTextView;
        TextView foodQuantityTextView;
        ImageView imageFoodImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameTextView = itemView.findViewById(R.id.food_name);
            foodQuantityTextView = itemView.findViewById(R.id.food_quantity);
            imageFoodImage = itemView.findViewById(R.id.food_image);

            // Initialize other views here
        }
    }
}
