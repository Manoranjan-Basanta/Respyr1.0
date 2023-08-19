package com.humorstech.respyr.reading;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humorstech.respyr.R;

import java.util.List;

public class WorkoutSliderAdapter extends RecyclerView.Adapter<WorkoutSliderAdapter.ViewHolder> {

    private List<WorkoutSliderDataModel> dataList;
    private int i=0;

    public WorkoutSliderAdapter(List<WorkoutSliderDataModel> dataList, int i) {
        this.dataList = dataList;
        this.i = i;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout for each page
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.work_out_mini_card, parent, false);
       if(i==2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_page_workout_card, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutSliderDataModel data = dataList.get(position);
        holder.textView1.setText(data.getTitle());
        holder.textView2.setText(data.getDescription());
        holder.linearLayout.setBackgroundResource(data.getImage());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.txt_work_out_name);
            textView2 = itemView.findViewById(R.id.txt_work_out_time);
            linearLayout = itemView.findViewById(R.id.layout_work_out_number);
        }
    }
}

