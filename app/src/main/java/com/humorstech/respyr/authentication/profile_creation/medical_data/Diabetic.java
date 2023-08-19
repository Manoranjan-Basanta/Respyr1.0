package com.humorstech.respyr.authentication.profile_creation.medical_data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.utills.ProfileCreationData;
import com.loopj.android.http.RequestParams;

public class Diabetic extends AppCompatActivity {



    private boolean isDiabetic;
    private String fastingBloodSugar;

    private Button buttonNext;


    private TextView txtCard1;
    private TextView txtCard2;
    private TextView txtCard3;
    private TextView txtCard4;
    private TextView txtCard5;

    public Diabetic(){
        isDiabetic=false;
        fastingBloodSugar="";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetic);
        StatusBarColor statusBarColor = new StatusBarColor(Diabetic.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));
        init();
    }



    private void init(){
        setReferences();

    }
    private void setReferences(){

        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 4,3);

        txtCard1=findViewById(R.id.txt_card1);
        txtCard2=findViewById(R.id.txt_card2);
        txtCard3=findViewById(R.id.txt_card3);
        txtCard4=findViewById(R.id.txt_card4);
        txtCard5=findViewById(R.id.txt_card5);
        buttonNext=findViewById(R.id.button_next);

        txtCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDiabetic(1);

            }
        });

        txtCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDiabetic(2);
            }
        });


        txtCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDiabetic(3);
            }
        });


        txtCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDiabetic(4);
            }
        });


        txtCard5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDiabetic(5);
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store gender id for profile creation
                SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(ProfileCreationData.SUGAR_LEVEL, String.valueOf(fastingBloodSugar));
                editor.apply();


                Intent intent = new Intent(getApplicationContext(), Conditions.class);
                startActivity(intent);
                finish();

            }
        });

    }



    @SuppressLint("UseCompatLoadingForDrawables")
    private void selectDiabetic(int i){
        Drawable active = ContextCompat.getDrawable(this, R.drawable.radio_active);
        Drawable inactive = ContextCompat.getDrawable(this, R.drawable.radio_inactive);

        switch (i){
            case  1 :



                txtCard1.setBackground(getResources().getDrawable(R.drawable.blood_test_card_active));
                txtCard1.setCompoundDrawablesRelativeWithIntrinsicBounds(active, null, null, null);

                fastingBloodSugar = txtCard1.getText().toString();


                txtCard2.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard3.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard4.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard5.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));

                txtCard2.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard3.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard4.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard5.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);


                break;
            case  2 :
                txtCard2.setBackground(getResources().getDrawable(R.drawable.blood_test_card_active));
                txtCard2.setCompoundDrawablesRelativeWithIntrinsicBounds(active, null, null, null);
                fastingBloodSugar = txtCard2.getText().toString();

                txtCard1.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard3.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard4.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard5.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));

                txtCard1.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard3.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard4.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard5.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);


                break;
            case  3 :
                txtCard3.setBackground(getResources().getDrawable(R.drawable.blood_test_card_active));
                txtCard3.setCompoundDrawablesRelativeWithIntrinsicBounds(active, null, null, null);
                fastingBloodSugar = txtCard3.getText().toString();

                txtCard1.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard2.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard4.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard5.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));

                txtCard1.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard2.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard4.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard5.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);


                break;
            case  4 :
                txtCard4.setBackground(getResources().getDrawable(R.drawable.blood_test_card_active));
                txtCard4.setCompoundDrawablesRelativeWithIntrinsicBounds(active, null, null, null);
                fastingBloodSugar = txtCard4.getText().toString();

                txtCard1.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard2.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard3.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard5.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));



                txtCard1.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard2.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard3.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard5.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);


                break;
            case  5 :
                txtCard5.setBackground(getResources().getDrawable(R.drawable.blood_test_card_active));
                txtCard5.setCompoundDrawablesRelativeWithIntrinsicBounds(active, null, null, null);
                fastingBloodSugar = txtCard5.getText().toString();

                txtCard1.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard2.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard3.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard4.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));


                txtCard1.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard2.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard3.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);
                txtCard4.setCompoundDrawablesRelativeWithIntrinsicBounds(inactive, null, null, null);

                break;
        }
    }




    private void showTxtOnError(TextView textView, String message){
        textView.setVisibility(View.VISIBLE);
        textView.setText(message);
        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, textView.getBottom());
            }
        });
    }

    private void saveMedicalData(){
        RequestParams params = new RequestParams();
        params.put("Login_id", "12");
        params.put("profile_id", "12");
        params.put("Login_id", "12");
        params.put("Login_id", "12");
        params.put("Login_id", "12");
    }




}