package com.humorstech.respyr.authentication.profile_creation.medical_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.utills.ProfileCreationData;

public class BloodTest extends AppCompatActivity {

    private Button buttonNext;
    private TextView txtCard1, txtCard2, txtCard3, txtCard4;

    private boolean isCardSelected;
    private String bloodTestValue;
    private String profileId;

    public BloodTest(){
        isCardSelected=false;
        bloodTestValue=null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_test);
        StatusBarColor statusBarColor = new StatusBarColor(BloodTest.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();
    }
    private void init(){
        setReferences();
    }
    private void setReferences(){
        txtCard1=findViewById(R.id.txt_card1);
        txtCard2=findViewById(R.id.txt_card2);
        txtCard3=findViewById(R.id.txt_card3);
        txtCard4=findViewById(R.id.txt_card4);
        buttonNext=findViewById(R.id.button_next);

        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 4,1);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCardSelected){

                    Intent intent;
                    // store gender id for profile creation
                    SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ProfileCreationData.BLOOD_TEST, String.valueOf(bloodTestValue));
                    editor.apply();


                    if (bloodTestValue.equals("Never")){
                        intent = new Intent(getApplicationContext(), Conditions.class);
                    }else{
                        intent = new Intent(getApplicationContext(), Diabetic.class);
                    }
                    startActivity(intent);

                }else{
                    Toast.makeText(BloodTest.this, "Please select one of the following options", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardImplementation(1);

            }
        });
        txtCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardImplementation(2);
            }
        });
        txtCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardImplementation(3);
            }
        });
        txtCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardImplementation(4);
            }
        });

    }

    private void cardImplementation(int number){
        isCardSelected=true;

        Drawable radio_inactive = getResources().getDrawable(R.drawable.radio_inactive);
        Drawable radio_active  = getResources().getDrawable(R.drawable.radio_active);


        switch (number){
            case  1 :
                // active
                bloodTestValue="Within 3 months";
                txtCard1.setBackground(getResources().getDrawable(R.drawable.blood_test_card_active));
                txtCard1.setTextColor(getColor(R.color.primary));
                txtCard1.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_active, null, null, null);


                // in active
                txtCard2.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard3.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard4.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));

                txtCard2.setTextColor(getColor(R.color.black));
                txtCard3.setTextColor(getColor(R.color.black));
                txtCard4.setTextColor(getColor(R.color.black));

                txtCard2.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);
                txtCard3.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);
                txtCard4.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);


                break;
            case  2 :
                // active
                bloodTestValue="Within 6 months";
                txtCard2.setBackground(getResources().getDrawable(R.drawable.blood_test_card_active));
                txtCard2.setTextColor(getColor(R.color.primary));
                txtCard2.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_active, null, null, null);

                // in active
                txtCard1.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard3.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard4.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));

                txtCard1.setTextColor(getColor(R.color.black));
                txtCard3.setTextColor(getColor(R.color.black));
                txtCard4.setTextColor(getColor(R.color.black));

                txtCard1.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);
                txtCard3.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);
                txtCard4.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);

                break;
            case  3 :
                // active
                bloodTestValue="Over 6 months";
                txtCard3.setBackground(getResources().getDrawable(R.drawable.blood_test_card_active));
                txtCard3.setTextColor(getColor(R.color.primary));
                txtCard3.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_active, null, null, null);

                // in active
                txtCard2.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard1.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard4.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));

                txtCard2.setTextColor(getColor(R.color.black));
                txtCard1.setTextColor(getColor(R.color.black));
                txtCard4.setTextColor(getColor(R.color.black));

                txtCard2.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);
                txtCard1.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);
                txtCard1.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);

                break;
            case  4 :
                // active
                bloodTestValue="Never";
                txtCard4.setBackground(getResources().getDrawable(R.drawable.blood_test_card_active));
                txtCard4.setTextColor(getColor(R.color.primary));
                txtCard4.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_active, null, null, null);

                // in active
                txtCard2.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard3.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));
                txtCard1.setBackground(getResources().getDrawable(R.drawable.blood_test_card_inactive));

                txtCard2.setTextColor(getColor(R.color.black));
                txtCard3.setTextColor(getColor(R.color.black));
                txtCard1.setTextColor(getColor(R.color.black));

                txtCard2.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);
                txtCard3.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);
                txtCard1.setCompoundDrawablesRelativeWithIntrinsicBounds(radio_inactive, null, null, null);

        }
    }

}