package com.humorstech.respyr.authentication.profile_creation.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.utills.ProfileCreationData;

public class Gender extends AppCompatActivity {

    private LinearLayout llMale, llFemale;
    private Button buttonBack, buttonNext;
    private String userGender;

    private static final String TAG = "Gender--->";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        StatusBarColor statusBarColor = new StatusBarColor(Gender.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));
        init();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    private void init(){
        userGender="Female";
        initVars();
        onClicks();

        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 4,1);


        // Get the screen density label
        String densityLabel = getScreenDensityLabel();

        // Use the screen density label as needed
        // For example, you can log the density label
        Log.d("Density", "Screen Density: " + densityLabel);
    }

    private String getScreenDensityLabel() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int densityDpi = metrics.densityDpi;

        String densityLabel;
        if (densityDpi >= DisplayMetrics.DENSITY_XXXHIGH) {
            densityLabel = "xxxhdpi";
        } else if (densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
            densityLabel = "xxhdpi";
        } else if (densityDpi >= DisplayMetrics.DENSITY_XHIGH) {
            densityLabel = "xhdpi";
        } else if (densityDpi >= DisplayMetrics.DENSITY_HIGH) {
            densityLabel = "hdpi";
        } else if (densityDpi >= DisplayMetrics.DENSITY_MEDIUM) {
            densityLabel = "mdpi";
        } else {
            densityLabel = "ldpi";
        }

        return densityLabel;
    }
    private void initVars(){
        llMale=findViewById(R.id.button_male);
        llFemale=findViewById(R.id.button_female);
        buttonBack=findViewById(R.id.button_back);
        buttonNext=findViewById(R.id.button_next);
    }
    private void onClicks(){
        llMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderImplementation("Male");
            }
        });
        llFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderImplementation("Female");
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    // store gender id for profile creation
                    SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ProfileCreationData.GENDER, userGender);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), Age.class);
                    startActivity(intent);

                }catch (Exception e){
                    Log.d(TAG, e.getMessage());
                }
            }
        });

    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void genderImplementation(String gender){
        TextView txtMale, txtFemale;

        txtMale=findViewById(R.id.txt_male);
        txtFemale=findViewById(R.id.txt_female);
        if (gender.equals("Male")){
            llMale.setBackground(getResources().getDrawable(R.drawable.bg_gender_active));
            llFemale.setBackground(getResources().getDrawable(R.drawable.bg_gender));
            userGender="Male";
            txtMale.setTextColor(getResources().getColor(R.color.white));
            txtFemale.setTextColor(getResources().getColor(R.color.primary));

        }else if (gender.equals("Female")){
            llFemale.setBackground(getResources().getDrawable(R.drawable.bg_gender_active));
            llMale.setBackground(getResources().getDrawable(R.drawable.bg_gender));
            userGender="Female";
            txtMale.setTextColor(getResources().getColor(R.color.primary));
            txtFemale.setTextColor(getResources().getColor(R.color.white));
        }

    }
}