package com.humorstech.respyr.results;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;

public class DetailedLifeStyle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_life_style);
        StatusBarColor statusBarColor= new StatusBarColor(DetailedLifeStyle.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));
    }
}