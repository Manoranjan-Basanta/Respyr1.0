package com.humorstech.respyr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.humorstech.respyr.daily_routine.DailyRoutinForm;
import com.humorstech.respyr.notification.Main;
import com.humorstech.respyr.profile.Profile;
import com.humorstech.respyr.reading.SelectUser;
import com.humorstech.respyr.reading.Tempdashboard;
import com.humorstech.respyr.utills.ActiveProfile;
import com.humorstech.respyr.utills.LoginUtils;
import com.humorstech.respyr.utills.ReadingParameters;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import java.util.Objects;

public class Dashboard extends AppCompatActivity {


    private double healthScore;
    private double diabeticScore;
    private double vitalScore;
    private double blowScore;
    private double OverallLifeStyleScore;
    private double detailed_nutrition_score;
    private String name;
    private String BMISuggestions;
    private String date;
    private String time;
    private TextView txtLog;

    private ShimmerFrameLayout shimmerFrameLayout;

    private String profileId,loginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        StatusBarColor statusBarColor= new StatusBarColor(  Dashboard.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));


        init();

        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();


        SharedPreferences sharedPreferences = getSharedPreferences("result_data", Context.MODE_PRIVATE);
        // Retrieve the data using the keys
        healthScore = Double.parseDouble(sharedPreferences.getString("health_score", "0"));
        diabeticScore = Double.parseDouble(sharedPreferences.getString("diabetic_score", "0"));
        vitalScore = Double.parseDouble(sharedPreferences.getString("vital_score", "0"));
        blowScore = Double.parseDouble(sharedPreferences.getString("blowScore", "0"));
        OverallLifeStyleScore = Double.parseDouble(sharedPreferences.getString("overall_lifestyle_score", "0"));
        detailed_nutrition_score = Double.parseDouble(sharedPreferences.getString("OverallNutrientScore", "0"));
        name = sharedPreferences.getString("name", "");
        BMISuggestions = sharedPreferences.getString("BMISuggestions", "null");
        date = sharedPreferences.getString("curr_date", "null");
        time = sharedPreferences.getString("curr_time", "null");


        txtLog = findViewById(R.id.txt_log);
        txtLog.setMovementMethod(new ScrollingMovementMethod());


        txtLog.append("\n");
        txtLog.append("Clinical Scores--------------------------------------"+"\n");
        txtLog.append("Overall Health Score--->"+healthScore + "\n");
        txtLog.append("Diabetic Score--->"+diabeticScore + "\n");
        txtLog.append("Blow Score--->"+blowScore + "\n");
        txtLog.append("Vital Score--->"+vitalScore + "\n");
        txtLog.append("LifeStyle Scores---------------------------------------"+"\n");
        txtLog.append("LifeStyle Score--->"+OverallLifeStyleScore + "\n");
        txtLog.append("Nutrition Health Score--->"+detailed_nutrition_score + "\n");
        txtLog.append("Date--->"+date + "\n");
        txtLog.append("Time--->"+time + "\n");

        setCurrentDateAndTime();
        perform3Scores(blowScore, vitalScore, diabeticScore);
        performLifeStyle(OverallLifeStyleScore, detailed_nutrition_score);
        performOverallHealthScore(healthScore);

        LinearLayout takeTest=findViewById(R.id.button_take_test);
        takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), DailyRoutinForm.class);
                startActivity(intent);

            }
        });

        Button button =findViewById(R.id.button_view_analysis);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 0);
                startActivity(intent);
            }
        });

        LinearLayout card1 =findViewById(R.id.card_lungs);
        LinearLayout card2 =findViewById(R.id.card_vital);
        LinearLayout card3 =findViewById(R.id.card_diabetic);
        LinearLayout card4 =findViewById(R.id.card_nutrition);
        LinearLayout card5 =findViewById(R.id.card_activity);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 2);
                startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 3);
                startActivity(intent);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 1);
                startActivity(intent);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 5);
                startActivity(intent);
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 4);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences3 = getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);
        loginId = sharedPreferences3.getString(ActiveProfile.LOGIN_ID, null);
        profileId = sharedPreferences3.getString(ActiveProfile.PROFILE_ID, null);
        String gender = sharedPreferences3.getString(ActiveProfile.GENDER, null);

        setToolBar(gender);

    }

    private void setToolBar(String gender){

        ImageView imgProfileAv =findViewById(R.id.img_toolbar_av);

        int profileColor1 = ContextCompat.getColor(getApplicationContext(), R.color.profile1);
        int profileColor2 = ContextCompat.getColor(getApplicationContext(), R.color.profile2);
        int profileColor3 = ContextCompat.getColor(getApplicationContext(), R.color.profile3);
        int profileColor4 = ContextCompat.getColor(getApplicationContext(), R.color.profile4);

        // Create a color filter with the specified tint color and mode (SRC_ATOP in this case)
        PorterDuffColorFilter colorFilter1 = new PorterDuffColorFilter(profileColor1, PorterDuff.Mode.SRC_ATOP);
        PorterDuffColorFilter colorFilter2 = new PorterDuffColorFilter(profileColor2, PorterDuff.Mode.SRC_ATOP);
        PorterDuffColorFilter colorFilter3 = new PorterDuffColorFilter(profileColor3, PorterDuff.Mode.SRC_ATOP);
        PorterDuffColorFilter colorFilter4 = new PorterDuffColorFilter(profileColor4, PorterDuff.Mode.SRC_ATOP);


        if (gender!=null){
            if (gender.equals("male") || gender.equals("Male")){
                imgProfileAv.setImageResource(R.drawable.profile_av1);
            }else if (imgProfileAv.equals("female") || gender.equals("Female")){
                imgProfileAv.setImageResource(R.drawable.profile_av2);
            }
        }
        if (Objects.equals(profileId, "profile1")){
            imgProfileAv.getBackground().setColorFilter(colorFilter1);
        }else if(Objects.equals(profileId, "profile2")){
            imgProfileAv.getBackground().setColorFilter(colorFilter2);
        }else if(Objects.equals(profileId, "profile3")){
            imgProfileAv.getBackground().setColorFilter(colorFilter3);
        }else if(Objects.equals(profileId, "profile4")){
            imgProfileAv.getBackground().setColorFilter(colorFilter4);
        }

    }

    private void setCurrentDateAndTime(){
        TextView txtCurrentDate = findViewById(R.id.txt_current_date);
        TextView txtCurrentTime= findViewById(R.id.txt_current_time);
        txtCurrentDate.setText(date);
        txtCurrentTime.setText(time);
    }

    private void init(){
        onClicks();
    }

    private void onClicks() {
        RelativeLayout buttonProfile=findViewById(R.id.button_profile);
        RelativeLayout buttonNotification=findViewById(R.id.button_notification);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
        buttonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
            }
        });


    }

    private void perform3Scores(double lungsScore, double vitalScore, double diabeticScore){

        TextView txtLungsScore =findViewById(R.id.txt_lungs_score);
        TextView txtLungsStatus =findViewById(R.id.txt_lungs_status);
        TextView txtLungsMessage=findViewById(R.id.txt_lungs_message);
        RoundedProgressBar progressLungs =findViewById(R.id.progress_lungs);

        TextView txtVitalScore =findViewById(R.id.txt_vital_score);
        TextView txtVitalStatus=findViewById(R.id.txt_vitals_status);
        TextView txtVitalMessage =findViewById(R.id.txt_vitals_message);
        RoundedProgressBar  progressVital=findViewById(R.id.progress_vitals);

        TextView txtDiabeticScore =findViewById(R.id.txt_diabetic_score);
        TextView txtDiabeticStatus =findViewById(R.id.txt_diabetic_status);
        TextView txtDiabeticMessage=findViewById(R.id.txt_diabetic_message);
        RoundedProgressBar  progressDiabetic=findViewById(R.id.progress_diabetic);

        setProgress(lungsScore, txtLungsScore, progressLungs, txtLungsStatus,txtLungsMessage);
        setProgress(vitalScore, txtVitalScore, progressVital, txtVitalStatus,txtVitalMessage);
        setProgress(diabeticScore, txtDiabeticScore, progressDiabetic, txtDiabeticStatus,txtDiabeticMessage);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerFrameLayout.hideShimmer();
            }
        },500);
    }

    private void setProgress(double score, TextView textScore, RoundedProgressBar roundedProgressBar, TextView status,TextView message ){



        final int unHealthy = 60;
        final int moderate = 80;
        final int healthy = 100;
        final String[] titles = {"Good!","Fair!","Poor!"};
        final String[] subTitles = {"Everything looks good!","Monitor daily for a week!","Come back in a week!"};



        // set progress to text and progress bar
        double roundedValue = Math.round(score);

        if (score - Math.floor(score) >= 0.5) {
            roundedValue = Math.ceil(score);
        }

        textScore.setText(String.valueOf((int)roundedValue)+"%");
        roundedProgressBar.setProgressPercentage(roundedValue, true);



        if(score<=unHealthy){
            status.setText(titles[2]);
            message.setText(subTitles[2]);
            status.setTextColor(getResources().getColor(R.color.red));
            textScore.setTextColor(getResources().getColor(R.color.red));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.red));
        }else if (score<moderate){
            status.setText(titles[1]);
            message.setText(subTitles[1]);
            status.setTextColor(getResources().getColor(R.color.yellow));
            textScore.setTextColor(getResources().getColor(R.color.yellow));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.yellow));
        }else if(score<=healthy){
            status.setText(titles[0]);
            message.setText(subTitles[0]);
            status.setTextColor(getResources().getColor(R.color.green));
            textScore.setTextColor(getResources().getColor(R.color.green));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.green));
        }


    }

    private void performLifeStyle(double lifeStyleScore, double nutritionScore){

        TextView txtLifeStyleScore =findViewById(R.id.txt_activity_score);
        TextView txtLifeStyleStatus =findViewById(R.id.txt_activity_status);
        TextView txtLifeStyleMessage=findViewById(R.id.txt_activity_message);
        RoundedProgressBar  progressLifeStyle =findViewById(R.id.progress_activity);

        TextView txtNutritionScore =findViewById(R.id.txt_nutrition_score);
        TextView txtNutritionStatus =findViewById(R.id.txt_nutrition_status);
        TextView txtNutritionMessage=findViewById(R.id.txt_nutrition_message);
        RoundedProgressBar  progressNutrition=findViewById(R.id.progress_nutrition);

        setLifeStyleScores(1 ,lifeStyleScore,txtLifeStyleScore,progressLifeStyle,txtLifeStyleStatus,txtLifeStyleMessage );
        setLifeStyleScores(2 ,nutritionScore,txtNutritionScore,progressNutrition,txtNutritionStatus,txtNutritionMessage );
    }

    private void setLifeStyleScores(int i ,double score,TextView txtScore,RoundedProgressBar roundedProgressBar, TextView txtStatus, TextView txtMessage ){

        final int unHealthy = 70;
        final int moderate = 80;
        final int healthy = 100;
        final String[] titles = {"Good!","Fair!","Poor!"};
        final String[] subTitles = {"You are on fire","Keep pushing","Start moving"};
        final String[] subTitles1 = {"Focus on food","Eat better","Fueling up right"};




        // set progress to text and progress bar
        double roundedValue = Math.round(score);

        if (score - Math.floor(score) >= 0.5) {
            roundedValue = Math.ceil(score);
        }
        txtScore.setText(String.valueOf((int)roundedValue)+"%");
        roundedProgressBar.setProgressPercentage(roundedValue, true);


        if(score<=unHealthy){
            txtStatus.setText(titles[2]);

            if (i==1){
                txtMessage.setText(subTitles[2]);
            }else{
                txtMessage.setText(subTitles1[2]);
            }

            txtStatus.setTextColor(getResources().getColor(R.color.red));
            txtScore.setTextColor(getResources().getColor(R.color.red));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.red));
        }else if (score<moderate){
            txtStatus.setText(titles[1]);

            if (i==1){
                txtMessage.setText(subTitles[1]);
            }else{
                txtMessage.setText(subTitles1[1]);
            }


            txtStatus.setTextColor(getResources().getColor(R.color.yellow));
            txtScore.setTextColor(getResources().getColor(R.color.yellow));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.yellow));
        }else if(score<=healthy){
            txtStatus.setText(titles[0]);


            if (i==1){
                txtMessage.setText(subTitles[0]);
            }else{
                txtMessage.setText(subTitles1[0]);
            }


            txtStatus.setTextColor(getResources().getColor(R.color.green));
            txtScore.setTextColor(getResources().getColor(R.color.green));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.green));
        }


    }

    @SuppressLint("SetTextI18n")
    private void performOverallHealthScore(double overallHealthScore){

        TextView txtProfileName =findViewById(R.id.txt_profile_name);
        TextView txtOverallHealthScore =findViewById(R.id.txt_overall_health_score);
        RoundedProgressBar progressOverallHealthScore =findViewById(R.id.progress_overall_health_score);


        /// set profile name

        //Shubham,
        //Your Overall Health Score is
        String profileName = name;
        txtProfileName.setText(profileName + ",\nYour Overall Health Score is");

        final int unHealthy = 60;
        final int moderate = 80;
        final int healthy = 100;

        // set progress to text and progress bar
        double roundedValue = Math.round(overallHealthScore);

        if (overallHealthScore - Math.floor(overallHealthScore) >= 0.5) {
            roundedValue = Math.ceil(overallHealthScore);
        }
        txtOverallHealthScore.setText(String.valueOf((int)roundedValue)+"%");
        progressOverallHealthScore.setProgressPercentage(roundedValue, true);

        if(overallHealthScore<=unHealthy){
            progressOverallHealthScore.setProgressDrawableColor(getResources().getColor(R.color.red));
            txtOverallHealthScore.setTextColor(getResources().getColor(R.color.red));
        }else if (overallHealthScore<moderate){
            progressOverallHealthScore.setProgressDrawableColor(getResources().getColor(R.color.yellow));
            txtOverallHealthScore.setTextColor(getResources().getColor(R.color.yellow));
        }else if(overallHealthScore<=healthy){
            progressOverallHealthScore.setProgressDrawableColor(getResources().getColor(R.color.green));
            txtOverallHealthScore.setTextColor(getResources().getColor(R.color.green));
        }
    }
}