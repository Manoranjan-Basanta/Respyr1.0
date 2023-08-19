package com.humorstech.respyr.daily_routine.search;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.humorstech.respyr.R;

import java.util.List;

public class FrequentlyFoodAdapter extends RecyclerView.Adapter<FrequentlyFoodAdapter.ViewHolder>{


    private Context context;
    private Activity activity;
    private List<FrequentlyFoodData> itemList;

    public FrequentlyFoodAdapter(Activity activity, List<FrequentlyFoodData> itemList) {
        this.context = activity.getApplicationContext();
        this.itemList = itemList;
        this.activity = activity;
    }

    private void showBottomSheet(int id) {
        SelectFoodSheet selectFoodSheet = new SelectFoodSheet(activity,activity);
        selectFoodSheet.show(id);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frequently_food_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FrequentlyFoodData item = itemList.get(position);

        holder.foodNameTextView.setText(item.getFoodName());
       // holder.foodQuantityTextView.setText(item.getFoodQuantity());

        Glide.with(context)
                .load(item.getFoodImageLink())
                .placeholder(R.drawable.food_image_not_found)
                .into(holder.foodImageView);

        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showBottomSheet(item.getItemId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImageView;
        TextView foodNameTextView;
        TextView foodCategoryTextView;
        TextView foodQuantityTextView;
        ImageButton buttonAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImageView = itemView.findViewById(R.id.food_image_view1);
            foodNameTextView = itemView.findViewById(R.id.food_name_text_view1);
            foodCategoryTextView = itemView.findViewById(R.id.food_quantity_text_view1);
            buttonAdd = itemView.findViewById(R.id.button_add_food1);
        }

    }


}
