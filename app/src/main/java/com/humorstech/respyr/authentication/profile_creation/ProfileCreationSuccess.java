package com.humorstech.respyr.authentication.profile_creation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.humorstech.respyr.Dashboard;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.reading.BeforeReading;
import com.humorstech.respyr.utills.LoginUtils;

public class ProfileCreationSuccess extends AppCompatActivity {

    private String loginID,profileID,name;

    private TextView txtProfileName;


    private Button buttonStarTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_sucess);
        StatusBarColor statusBarColor = new StatusBarColor(ProfileCreationSuccess.this);
        statusBarColor.setDarkColor(getResources().getColor(R.color.primary));
        init();
    }



    private void init(){
        setReferences();
        setProfileName();
        onClick();
    }

    private void onClick(){
        buttonStarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setReferences(){
        txtProfileName=findViewById(R.id.txt_profile_name);
        buttonStarTest=findViewById(R.id.button_start_test);
    }
    @SuppressLint("SetTextI18n")
    private void setProfileName(){
        SharedPreferences sharedPreferences = getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
        loginID = sharedPreferences.getString(LoginUtils.LOGIN_ID, "0");
        profileID = sharedPreferences.getString(LoginUtils.PROFILE_ID, "0");
        name = sharedPreferences.getString(LoginUtils.USER_NAME, "0");
        txtProfileName.setText(name);
    }

}