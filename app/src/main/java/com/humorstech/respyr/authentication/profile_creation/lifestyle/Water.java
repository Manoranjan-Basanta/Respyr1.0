package com.humorstech.respyr.authentication.profile_creation.lifestyle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.utills.ProfileCreationData;
import com.humorstech.respyr.utills.ProfileUtils;

public class Water extends AppCompatActivity{

    private Button buttonNext, buttonBack;


    private static final String TAG = "Water";
    private String profileId;

    int waterCount=0;
    double waterInMilliliters;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        StatusBarColor statusBarColor = new StatusBarColor(Water.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init(){
        initVars();
        onClicks();
        performWaterCounter();
    }
    private void initVars(){
        buttonNext=findViewById(R.id.button_next);
        buttonBack=findViewById(R.id.button_back);

        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 5,1);
    }
    private void onClicks(){
        buttonNext.setOnClickListener(v -> {

            try {

                // store gender id for profile creation
                SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(ProfileCreationData.WATER_CONSUMPTION, String.valueOf(waterCount));
                editor.apply();


                SharedPreferences sharedPreferences2 = getSharedPreferences("WATER", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.putString("WC", String.valueOf(waterCount));
                editor2.apply();




                Intent intent = new Intent(getApplicationContext(), Hobbies.class);
                startActivity(intent);


            }catch (Exception e){
                Log.d(TAG, e.getMessage());
            }
        });

        buttonBack.setOnClickListener(v -> onBackPressed());
    }
    private void performWaterCounter(){
        ImageButton waterGlassMinus =findViewById(R.id.button_water_minus);
        ImageButton waterGlassPlus =findViewById(R.id.button_water_plus);

        updateWaterCount();

        waterGlassMinus.setOnClickListener(v -> {
            if (waterCount > 0) {
                waterCount--;
                updateWaterCount();
            }
        });

        waterGlassPlus.setOnClickListener(v -> {
            waterCount++;
            updateWaterCount();
        });

    }

    @SuppressLint("SetTextI18n")
    private void updateWaterCount() {
        TextView txtWaterGlasses =findViewById(R.id.txt_water_glasses);
        double waterInLiters = waterCount * 0.25; // Convert glasses to liters
        waterInMilliliters = waterCount * 250; // Convert glasses to milliliters

        String msg = "ML";
        if (waterInLiters > 0.99){
             msg = "L";
        }
        txtWaterGlasses.setText(waterCount + " glass"+" ("+ waterInLiters +" "+msg+")");
    }

    private String getProfileId(){
        SharedPreferences sharedPreferences = getSharedPreferences(ProfileUtils.LOGIN, Context.MODE_PRIVATE);
        String savedProfileId = sharedPreferences.getString(ProfileUtils.PROFILE_ID ,null);
        return savedProfileId;
    }

    private String getLoginId(){
        SharedPreferences sharedPreferences = getSharedPreferences(ProfileUtils.LOGIN, Context.MODE_PRIVATE);
        String savedLoginId = sharedPreferences.getString(ProfileUtils.LOGIN_ID ,null);
        return savedLoginId;
    }

}