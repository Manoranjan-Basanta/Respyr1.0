package com.humorstech.respyr.reading.food_breakup;

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

public class FoodBreakupAdapter extends RecyclerView.Adapter<FoodBreakupAdapter.FoodViewHolder> {

    private List<FoodBreakupData> foodItemList;
    private Context context;
    int inTakeYpeCount=3;

    public FoodBreakupAdapter(List<FoodBreakupData> foodItemList, Context context, int inTakeYpeCount) {
        this.foodItemList = foodItemList;
        this.context = context;
        this.inTakeYpeCount = inTakeYpeCount;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_breakup_item, parent, false);
        return new FoodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodBreakupData foodItem = foodItemList.get(position);
        holder.foodNameTextView.setText(foodItem.getFoodName());
        holder.foodQuantityTextView.setText(foodItem.getFoodQuantity()+ " grams");
        String intakeType=foodItem.getFoodCalories();

        switch (inTakeYpeCount){
            case 1 :
                intakeType = foodItem.getFoodCalories() + " Kcal";
                break;
            case 2 : intakeType = foodItem.getFoodCarboHydrate() + " g";
                break;
            case 3 : intakeType = foodItem.getFoodProtein()+ " g";
                break;
            case 4 : intakeType = foodItem.getFoodFats()+ " g";
                break;
            case 5 : intakeType = foodItem.getFoodFibre()+ " g";

                break;
        }

        holder.foodCalories.setText(intakeType);
        Glide.with(context)
                .load(foodItem.getFoodImageLink())
                .placeholder(R.drawable.food_image_not_found) // placeholder image while loading
                .error(R.drawable.food_image_not_found) // error image if Glide fails to load
                .into(holder.foodImageView);

    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImageView;
        TextView foodNameTextView;
        TextView foodQuantityTextView;
        TextView foodCalories;
        ImageView imageFoodValIcon;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.food_breakup_image_view);
            foodNameTextView = itemView.findViewById(R.id.food_breakup_name_text_view);
            foodQuantityTextView = itemView.findViewById(R.id.food_breakup_quantity_text_view);
            foodCalories = itemView.findViewById(R.id.food_breakup_calories_text_view);
        }
    }
}
