package com.humorstech.respyr.profile.history;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humorstech.respyr.R;

import java.util.Collections;
import java.util.List;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;


public class BloodReportHistoryAdapter extends RecyclerView.Adapter<BloodReportHistoryHolder> {

    List<BloodReportHistoryDataModel> bloodReportList = Collections.emptyList();
    Context context;

    public BloodReportHistoryAdapter(List<BloodReportHistoryDataModel> data, Application application) {
        this.bloodReportList = data;
        this.context = application;
    }


    @NonNull
    @Override
    public BloodReportHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_report_history_card, parent, false);
        BloodReportHistoryHolder holder = new BloodReportHistoryHolder(v);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull BloodReportHistoryHolder holder, int position) {
        
        holder.txtTitle.setText(String.valueOf(bloodReportList.get(position).bloodReportTitle));
        holder.txtDateAndTime.setText(String.valueOf(bloodReportList.get(position).bloodReportDateAndTime));

        int bloodReportProgress = bloodReportList.get(position).bloodReportHealthScore;

        holder.txtProgressBloodReport.setText(String.valueOf(bloodReportProgress));
        holder.progressBloodReport.setMaxProgress(100);
        holder.progressBloodReport.setCurrentProgress(bloodReportProgress);

        if (bloodReportProgress <= 30){
            holder.progressBloodReport.setProgressColor(context.getColor(R.color.red));
        }else if(bloodReportProgress < 60){
            holder.progressBloodReport.setProgressColor(context.getColor(R.color.yellow));
        }else if(bloodReportProgress <=100){
            holder.progressBloodReport.setProgressColor(context.getColor(R.color.green));
        }



    }



    @Override
    public int getItemCount() {
        return bloodReportList.size();
    }
}

class  BloodReportHistoryHolder extends RecyclerView.ViewHolder {

     TextView txtTitle;
     TextView txtDateAndTime;
     CircularProgressIndicator progressBloodReport;
     TextView txtProgressBloodReport;
     Button buttonDelete;
     Button buttonView;
     LinearLayout llMainCard;
    BloodReportHistoryHolder(View itemView) {
        super(itemView);

        txtTitle= itemView.findViewById(R.id.txt_report_title);
        txtDateAndTime= itemView.findViewById(R.id.txt_report_date_and_time);
        buttonDelete= itemView.findViewById(R.id.button_delete_report);
        buttonView= itemView.findViewById(R.id.button_view_report);
        progressBloodReport= itemView.findViewById(R.id.progress_report_health_score);
        txtProgressBloodReport= itemView.findViewById(R.id.txt_report_health_score);
        llMainCard= itemView.findViewById(R.id.ll_report_card);

    }
}

