package com.humorstech.respyr.authentication.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.humorstech.respyr.R;

public class ExistingUser extends AppCompatActivity {

    private Button buttonUpDateProfile, buttonGoToDashboard;

    private static final String TAG = "ExistingUser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_user);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.light_secondary));
        init();
    }
    private void init(){
        initVars();
        onClicks();
    }

    private void initVars() {
        buttonUpDateProfile=findViewById(R.id.button_update_profile);
        buttonGoToDashboard=findViewById(R.id.button_go_to_dashboard);

    }
    private void onClicks() {
        buttonUpDateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), SelectProfile.class);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d(TAG, e.getMessage());
                }
            }
        });
        buttonGoToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), SelectProfile.class);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }
}