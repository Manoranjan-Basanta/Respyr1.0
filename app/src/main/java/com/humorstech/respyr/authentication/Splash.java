package com.humorstech.respyr.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.login.Login;
import com.humorstech.respyr.authentication.login.SelectProfile;
import com.humorstech.respyr.utills.LoginUtils;

public class Splash extends AppCompatActivity {


    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        StatusBarColor statusBarColor= new StatusBarColor(Splash.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        ImageView splashImage = findViewById(R.id.splash_image);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        splashImage.startAnimation(fadeInAnimation);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
        String loginId = sharedPreferences.getString(LoginUtils.LOGIN_ID, "null");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!loginId.equals("null")){
                    // already login
                    intent=new Intent(getApplicationContext(), SelectProfile.class);
                }else{
                    // logged out
                    intent=new Intent(getApplicationContext(), Login.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}