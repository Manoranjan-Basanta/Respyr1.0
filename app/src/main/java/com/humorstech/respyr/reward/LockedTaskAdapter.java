package com.humorstech.respyr.reward;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humorstech.respyr.R;

import java.util.Collections;
import java.util.List;

public class LockedTaskAdapter  extends RecyclerView.Adapter<ViewHolder>{

    List<LockedTaskDataModel> list = Collections.emptyList();
    Context context;

    public LockedTaskAdapter(List<LockedTaskDataModel> data, Application application) {
        this.list = data;
        this.context = application;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.locked_task_card, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTaskTitle.setText(String.valueOf(list.get(position).taskTitle));
        holder.txtTaskSubTitle.setText(String.valueOf(list.get(position).taskSubTitle));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class ViewHolder extends RecyclerView.ViewHolder {

    TextView txtTaskTitle;
    TextView txtTaskSubTitle;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTaskTitle = itemView.findViewById(R.id.txt_task_title);
        txtTaskSubTitle = itemView.findViewById(R.id.txt_task_sub_title);
    }
}