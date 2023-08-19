package com.humorstech.respyr.authentication.profile_creation.lifestyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.utills.ProfileCreationData;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

public class SleepHours extends AppCompatActivity {


    private EditText etSleepHours;
    private Button buttonNext, buttonBack;

    private static final String TAG = "SleepHours";


    private int sleepHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_hours);
        StatusBarColor statusBarColor = new StatusBarColor(SleepHours.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init(){
        initVars();
        seekBar();
        onClick();
    }
    private void initVars(){
        etSleepHours=findViewById(R.id.et_hours);
        buttonNext=findViewById(R.id.button_next);
        buttonBack=findViewById(R.id.button_back);

        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 5,4);
    }
    private void seekBar(){
        IndicatorSeekBar sleepSeekBar = findViewById(R.id.sleep_hour_bar);
        sleepSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                etSleepHours.setText(String.valueOf(seekParams.progress));
                sleepHours=seekParams.progress;

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }
    private void onClick(){
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    // store gender id for profile creation
                    SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ProfileCreationData.SLEEP_HOURS, String.valueOf(sleepHours));
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), MentalConditions.class);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d(TAG, e.getMessage());
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}