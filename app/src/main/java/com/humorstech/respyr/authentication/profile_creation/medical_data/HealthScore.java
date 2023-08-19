package com.humorstech.respyr.authentication.profile_creation.medical_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.profile_creation.ProfileCreationSuccess;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class HealthScore extends AppCompatActivity {

    private TextView txtScore, txtScoreTitle, txtScoreDescription;
    private CircularProgressIndicator healthScore;

    private static final String TAG = "HealthScore";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_score);
        StatusBarColor statusBarColor = new StatusBarColor(HealthScore.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));
        init();
    }
    private void init(){
        initVars();
        setScore(65);
        onClick();
    }
    private void initVars(){
        txtScore=findViewById(R.id.txt_health_score);
        txtScoreTitle=findViewById(R.id.txt_score_title);
        txtScoreDescription=findViewById(R.id.txt_score_description);
        healthScore =findViewById(R.id.health_score);
    }

    private void setScore(int score){

        txtScore.setText(String.valueOf(score));

        healthScore.setMaxProgress(100);
        healthScore.setCurrentProgress(score);
        if (score <= 30){
            healthScore.setProgressColor(getColor(R.color.red));
        }else if(score <= 60){
            healthScore.setProgressColor(getColor(R.color.yellow));
        }else if(score <=100){
            healthScore.setProgressColor(getColor(R.color.green));
        }
    }

    private void onClick(){
        Button buttonNext=findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), ProfileCreationSuccess.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }
}