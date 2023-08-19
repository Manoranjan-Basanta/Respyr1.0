package com.humorstech.respyr.profile.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.profile.Profile;

import org.json.JSONObject;

public class ProfileData extends AppCompatActivity {

    private static final String TAG = "ProfileData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);
        StatusBarColor statusBarColor= new StatusBarColor(  ProfileData.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();
    }
    private void init(){
        menuItemsClick();
        fetchLastUpdatedTime();
        onClicks();
    }

    private void menuItemsClick(){
        LinearLayout llMenu1 =findViewById(R.id.ll_menu_1);
        LinearLayout llMenu2 =findViewById(R.id.ll_menu_2);
        LinearLayout llMenu3 =findViewById(R.id.ll_menu_3);
        LinearLayout llMenu4 =findViewById(R.id.ll_menu_4);
        LinearLayout llMenu5 =findViewById(R.id.ll_menu_5);
        LinearLayout llMenu6 =findViewById(R.id.ll_menu_6);

        llMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileData.this, "1", Toast.LENGTH_SHORT).show();
            }
        });
        llMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileData.this, "2", Toast.LENGTH_SHORT).show();
            }
        });
        llMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileData.this, "3", Toast.LENGTH_SHORT).show();
            }
        });
        llMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileData.this, "4", Toast.LENGTH_SHORT).show();
            }
        });
        llMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileData.this, "5", Toast.LENGTH_SHORT).show();
            }
        });
        llMenu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileData.this, "6", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClicks(){
        ImageButton buttonBack =findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void fetchLastUpdatedTime(){
        TextView txtTime1 =findViewById(R.id.txt_time_1);
        TextView txtTime2 =findViewById(R.id.txt_time_2);;
        TextView txtTime3 =findViewById(R.id.txt_time_3);
        TextView txtTime4 =findViewById(R.id.txt_time_4);
        TextView txtTime5 =findViewById(R.id.txt_time_5);
        TextView txtTime6 =findViewById(R.id.txt_time_6);

        //php code : http://localhost/respyr/fatloss/time_diff.php
        String responseLastUpdatedTimes = "{\"timePersnoalInformation\":\"Last updated 2 days ago\",\"timeHobbies\":\"Last updated 2 days ago\",\"timeSleeping\":\"Last updated 2 days ago\",\"timeMentalState\":\"Last updated 2 days ago\",\"timeBloodTest\":\"Last updated 2 days ago\",\"timeMedicalHistory\":\"Last updated 2 days ago\"}";

        try {

            JSONObject decodedJson = new JSONObject(responseLastUpdatedTimes);

            // Retrieve values from the JSON object
            String timePersnoalInformation = decodedJson.getString("timePersnoalInformation");
            String timeHobbies = decodedJson.getString("timeHobbies");
            String timeSleeping = decodedJson.getString("timeSleeping");
            String timeMentalState = decodedJson.getString("timeMentalState");
            String timeBloodTest = decodedJson.getString("timeBloodTest");
            String timeMedicalHistory = decodedJson.getString("timeMedicalHistory");

            txtTime1.setText(timePersnoalInformation);
            txtTime2.setText(timeHobbies);
            txtTime3.setText(timeSleeping);
            txtTime4.setText(timeMentalState);
            txtTime5.setText(timeBloodTest);
            txtTime6.setText(timeMedicalHistory);

        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }

    }
}