package com.humorstech.respyr.daily_routine.search;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.humorstech.respyr.R;

import java.util.List;

public class SearchFoodDataAdapter extends RecyclerView.Adapter<SearchFoodDataAdapter.FoodViewHolder> {

    private List<SearchFoodData> foodList;
    private Context context;
    private Activity activity;
    private AdapterView.OnItemClickListener listener;
    public SearchFoodDataAdapter(List<SearchFoodData> foodList, Context context, Activity activity) {
        this.foodList = foodList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_layout, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        SearchFoodData foodItem = foodList.get(position);

        holder.foodNameTextView.setText(foodItem.getFoodName());

        Glide.with(context)
                .load(foodItem.getFoodImageLink())
                .placeholder(R.drawable.food_image_not_found)
                .into(holder.foodImageView);


        holder.buttonSelectFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet(foodItem.getItemId());
            }
        });
    }

    private void showBottomSheet(int id) {
        SelectFoodSheet selectFoodSheet = new SelectFoodSheet(activity,activity);
        selectFoodSheet.show(id);
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImageView;
        TextView foodNameTextView;
        TextView foodCategoryTextView;

        ImageView buttonSelectFood;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.food_image_view);
            foodNameTextView = itemView.findViewById(R.id.food_name_text_view);
            buttonSelectFood = itemView.findViewById(R.id.button_add_food);
        }
    }
}
