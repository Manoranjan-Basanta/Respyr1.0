package com.humorstech.respyr.reading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;

public class Suggestion extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutSliderAdapter adapter;
    private String LifeStyleSuggestion;
    private String BMISuggestion;
    private String breakfast_sugs;
    private String dinner_sugs;
    private String lunch_sugs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);


        StatusBarColor statusBarColor= new StatusBarColor(Suggestion.this);
        statusBarColor.setDarkColor(getResources().getColor(R.color.result_primary));



        SharedPreferences sharedPreferences = getSharedPreferences("result_data", Context.MODE_PRIVATE);
        breakfast_sugs = sharedPreferences.getString("breakfast_sugs", "");
        dinner_sugs = sharedPreferences.getString("dinner_sugs", "");
        lunch_sugs = sharedPreferences.getString("lunch_sugs", "");
        LifeStyleSuggestion = sharedPreferences.getString("LifeStyleSuggestion", "");
        BMISuggestion = sharedPreferences.getString("BMISuggestions", "");

        performBreakfastSuggestion();
        performLunchSuggestion();
        performDinnerSuggestion();
        performLifeStyleSuggestion();
        BMISuggestion();

        // Get the RecyclerView from the layout
        recyclerView = findViewById(R.id.recycler_view);

        // Create a list of data to populate the RecyclerView
        List<WorkoutSliderDataModel> dataList = new ArrayList<>();
        dataList.add(new WorkoutSliderDataModel("Modified Push-ups", "18 minutes", R.drawable.pushup2));
        dataList.add(new WorkoutSliderDataModel("Stretching", "Modified Squats", R.drawable.pushup2));
        // Create an instance of the custom adapter
        adapter = new WorkoutSliderAdapter(dataList,2);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        CircleIndicator2 indicator = findViewById(R.id.indicator);
        indicator.attachToRecyclerView(recyclerView, pagerSnapHelper);

        // Set the layout manager to display items linearly
        // Set the layout manager to display items horizontally
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        ImageButton buttonBack=findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void performBreakfastSuggestion(){
        String[] elements = breakfast_sugs.split("&");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<ul>");
        for (String element : elements) {
            stringBuilder.append("<li>").append(element).append("</li>");
        }
        stringBuilder.append("</ul>");

        String htmlString = stringBuilder.toString();

        TextView txtBreakFastSuggestion=findViewById(R.id.txt_breakfast);
        txtBreakFastSuggestion.setText(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY));
    }
    private void performLunchSuggestion(){
        String[] elements = lunch_sugs.split("&");
        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<String> foodNames = new ArrayList<>();






        stringBuilder.append("<ul>");
        for (String element : elements) {
            stringBuilder.append("<li>").append(element).append("</li>");
        }
        stringBuilder.append("</ul>");

        String htmlString = stringBuilder.toString();

        TextView txtLunchSuggestion=findViewById(R.id.txt_luch_suggestion);
        txtLunchSuggestion.setText(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY));
    }
    private void performDinnerSuggestion(){
        String[] elements = dinner_sugs.split("&");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<ul>");
        for (String element : elements) {
            stringBuilder.append("<li>").append(element).append("</li>");
        }
        stringBuilder.append("</ul>");

        String htmlString = stringBuilder.toString();

        TextView txtDinnerSuggestion=findViewById(R.id.txt_dinner_suggestion);
        txtDinnerSuggestion.setText(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY));
    }


    private void performLifeStyleSuggestion(){
        TextView waterIntakeSuggestion = findViewById(R.id.water_intake_suggestion);
        TextView sleepSuggestion = findViewById(R.id.sleep_suggestion);
        TextView exerciseSuggestion = findViewById(R.id.exercise_suggestion);

//        String json2 = "[\"Increase water consumption by 2822.09 ml\",\"Decrease sleep  by 1.00 Hr\"]";
//
//        String json  = LifeStyleSuggestion.replace("LifeStyleSuggestions : ", json2);
//
//        // Create a Gson instance
//        Gson gson = new Gson();
//
//        // Decode the JSON string into an array of strings
        String[] lifestyleSuggestions = {"Increase water consumption by 203.35 ml","Decrease sleep by 1.00 Hr, Increase exercise by 1.43 Min","Increase exercise by 1.43 Min"};

        // Set the text of each TextView to a lifestyle suggestion
        waterIntakeSuggestion.setText(lifestyleSuggestions[0]);
        sleepSuggestion.setText(lifestyleSuggestions[1]);
        exerciseSuggestion.setText(lifestyleSuggestions[2]);

    }
    private void BMISuggestion(){
        TextView txtBMISuggestion=findViewById(R.id.txt_bmi_sggestion);
        txtBMISuggestion.setText(BMISuggestion);
    }
}