package com.humorstech.respyr.authentication.profile_creation.lifestyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.utills.ProfileCreationData;

public class Hobbies extends AppCompatActivity {

    private Button buttonNext,buttonBack;

    private RadioGroup rgAlcohol, rgSmoking, rgEat, rgExc;
    private RadioButton rbAlcohol, rbSmoking, rbEat, rbExc;
    private String alcohol,smoking,eat,exc, mobileNumber;

    private TextView txtErr1, txtErr2, txtErr3, txtErr4;

    private String water;
    private String profileId;


    public Hobbies(){
        alcohol="null";
        smoking="null";
        exc="null";
        eat="null";
        water="null";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobbies);
        StatusBarColor statusBarColor = new StatusBarColor(Hobbies.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private void init(){
        initVars();
        radioButtons();
        onClick();
    }

    private void initVars() {
        rgAlcohol=findViewById(R.id.rg_alcohol);
        rgSmoking=findViewById(R.id.rg_smoking);
        rgEat=findViewById(R.id.rg_eat);
        rgExc=findViewById(R.id.rg_exc);

        //Buttons
        buttonNext=findViewById(R.id.button_next);
        buttonBack=findViewById(R.id.button_back);

        // TextVies
        txtErr1=findViewById(R.id.txt_err_1);
        txtErr2=findViewById(R.id.txt_err_2);
        txtErr3=findViewById(R.id.txt_err_3);
        txtErr4=findViewById(R.id.txt_err_4);

        // hide textview by default
        txtErr1.setVisibility(View.GONE);
        txtErr2.setVisibility(View.GONE);
        txtErr3.setVisibility(View.GONE);
        txtErr4.setVisibility(View.GONE);

        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 5,2);
    }


    private void onClick(){

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (alcohol.equals("NA")){
                    txtErr1.setVisibility(View.VISIBLE);
                    showTxtOnError(txtErr1, "Please select any one from the following");
                }else if(smoking.equals("NA")){
                    txtErr2.setVisibility(View.VISIBLE);
                    showTxtOnError(txtErr2, "Please select any one from the following");
                }else if(exc.equals("NA")){
                    txtErr4.setVisibility(View.VISIBLE);
                    showTxtOnError(txtErr3, "Please select any one from the following");
                }else if(eat.equals("NA")){
                    txtErr3.setVisibility(View.VISIBLE);
                    showTxtOnError(txtErr4, "Please select any one from the following");
                }else{

                    // store gender id for profile creation
                    SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ProfileCreationData.ALCOHOL, String.valueOf(alcohol));
                    editor.putString(ProfileCreationData.SMOKING, String.valueOf(smoking));
                    editor.putString(ProfileCreationData.EXERCISE, String.valueOf(exc));
                    editor.putString(ProfileCreationData.NOV_VEG, String.valueOf(eat));
                    editor.apply();



                    Intent intent = new Intent(getApplicationContext(), Food.class);
                    startActivity(intent);
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
    private void radioButtons(){
        rgAlcohol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = findViewById(checkedId);
                txtErr1.setVisibility(View.GONE);
                alcohol = radioButton.getText().toString();

            }
        });
        rgSmoking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = findViewById(checkedId);
                txtErr2.setVisibility(View.GONE);
                smoking = radioButton.getText().toString();

            }
        });
        rgExc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = findViewById(checkedId);
                txtErr4.setVisibility(View.GONE);
                exc = radioButton.getText().toString();

            }
        });
        rgEat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = findViewById(checkedId);
                txtErr3.setVisibility(View.GONE);
                eat = radioButton.getText().toString();

            }
        });
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
}