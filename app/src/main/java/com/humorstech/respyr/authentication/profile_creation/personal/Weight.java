package com.humorstech.respyr.authentication.profile_creation.personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.utills.ProfileCreationData;
import com.shawnlin.numberpicker.NumberPicker;

public class Weight extends AppCompatActivity {
    private Button buttonBack, buttonNext;
    private static final String TAG = "Weight";
    private NumberPicker weightPicker;


    private int Weight=55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        StatusBarColor statusBarColor = new StatusBarColor(Weight.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));
        init();
    }
    private void init(){
        setReferences();
        numberPicker();
        onClicks();
    }

    protected void onStart() {
        super.onStart();
    }

    private void setReferences(){
        buttonBack=findViewById(R.id.button_back);
        buttonNext=findViewById(R.id.button_next);
        weightPicker=findViewById(R.id.weight_picker);

        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 4,4);

    }
    private void onClicks(){
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
                    editor.putString(ProfileCreationData.WEIGHT, String.valueOf(Weight));
                    editor.apply();

                    Intent intent= new Intent(getApplicationContext(), BMI.class);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }
    private void numberPicker(){
        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_bold);
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.roboto);
        weightPicker.setSelectedTypeface(typeface);
        weightPicker.setTypeface(typeface2);
        weightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Weight=newVal;
            }
        });
    }
}