package com.humorstech.respyr.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.NetWork;
import com.humorstech.respyr.NetworkErrorSheet;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.login.Login;
import com.humorstech.respyr.authentication.login.Name;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Welcome extends AppCompatActivity {

    private Button buttonGetStarted;

    private static final String TAG = "Welcome";


    private ViewPager viewPager;
    private int currentPage = 0;
    private int NUM_PAGES = 4;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarColor statusBarColor= new StatusBarColor(Welcome.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();


    }
    private void init(){
        initVars();
        onClicks();
        slider();
    }
    private void initVars(){
        buttonGetStarted=findViewById(R.id.button_get_started);
    }
    public void onClicks(){
        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performNext(Welcome.this);

            }
        });
    }
    public void performNext(Context context){
        Dialogs.showLoadingDialog(context, "Loading");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialogs.hideLoadingDialog();
                if (NetWork.isNetworkAvailable(Welcome.this)){
                    move();
                }else{
                    NetworkErrorSheet.show(context, Welcome.this, Login.class);
                }
            }
        },1500);
    }

    private void move(){
        try {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
    }

    private void slider(){
        viewPager = findViewById(R.id.viewPager);
        SliderAdapter imageAdapter = new SliderAdapter(this);
        InfinitePagerAdapter infinitePagerAdapter = new InfinitePagerAdapter(imageAdapter);
        viewPager.setAdapter(infinitePagerAdapter);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                if (currentPage == Integer.MAX_VALUE - 1) {
                    viewPager.setCurrentItem(0, true);
                    currentPage = 1;
                } else {
                    viewPager.setCurrentItem(currentPage++, true);
                }
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 3000, 3000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}