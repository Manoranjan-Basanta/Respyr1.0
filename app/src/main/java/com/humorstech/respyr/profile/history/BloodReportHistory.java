package com.humorstech.respyr.profile.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.humorstech.respyr.R;

import java.util.ArrayList;
import java.util.List;

public class BloodReportHistory extends AppCompatActivity {

    List<BloodReportHistoryDataModel> data = new ArrayList<>();


    private ImageButton buttonAddNewReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_report_history);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        RecyclerView bloodReportHistoryList = findViewById(R.id.list_blood_report_history);

        List<BloodReportHistoryDataModel> data = fill_with_data();
        BloodReportHistoryAdapter adapter = new BloodReportHistoryAdapter(data,getApplication());
        bloodReportHistoryList.setAdapter(adapter);
        bloodReportHistoryList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Perform your refresh operation here
                // This method will be called when the user swipes down on the layout

                // Simulate a delay
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Stop the progress indicator
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000); // Delay for 2 seconds
            }
        });



        buttonAddNewReport=findViewById(R.id.button_add_new_blood_report);

        buttonAddNewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteReportBottomSheet deleteReportBottomSheet = new DeleteReportBottomSheet(BloodReportHistory.this);
                deleteReportBottomSheet.showCancelBottomSheet();
            }
        });






    }
    public List<BloodReportHistoryDataModel> fill_with_data() {
        data.add(
                new BloodReportHistoryDataModel(
                        1,
                        56,
                        "Blood Report 1 (12/7/2022)",
                        "Added 2 months ago",
                        65456456
                )
        );

        data.add(
                new BloodReportHistoryDataModel(
                        2,
                        65,
                        "Blood Report 2 (12/7/2022)",
                        "Added 2 months ago",
                        65456456
                )
        );


        return data;
    }
}