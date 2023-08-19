package com.humorstech.respyr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.viewpagerdots.DotsIndicator;
import com.aries.ui.view.tab.CommonTabLayout;
import com.aries.ui.view.tab.listener.CustomTabEntity;
import com.aries.ui.view.tab.listener.OnTabSelectListener;
import com.humorstech.respyr.notification.Main;
import com.humorstech.respyr.profile.Profile;
import com.humorstech.respyr.reward.Reward;
import com.humorstech.respyr.utills.BottomBarUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarColor statusBarColor= new StatusBarColor(  MainActivity.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();

    }
    private void init(){
        initVars();
        onClicks();
        reward();
        banner();
        bottomNavigation();
    }
    private void initVars() {
    }
    private void onClicks() {
        RelativeLayout buttonProfile=findViewById(R.id.button_profile);
        RelativeLayout buttonNotification=findViewById(R.id.button_notification);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
        buttonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
            }
        });



    }

    private void reward() {
        Button btnRewardMain =findViewById(R.id.button_reward_main);
        btnRewardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), Reward.class);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }
    private void banner(){
        ViewPager viewPager = findViewById(R.id.view_pages_banners);

        List<HeroBannerImageModel> images = new ArrayList<>();
        images.add(new HeroBannerImageModel(R.drawable.banner1));
        images.add(new HeroBannerImageModel(R.drawable.banner1));
        images.add(new HeroBannerImageModel(R.drawable.banner1));

        HeroBannerAdapter adapter = new HeroBannerAdapter(this, images);
        viewPager.setAdapter(adapter);

        DotsIndicator dotsIndicator = findViewById(R.id.dots);
        dotsIndicator.attachViewPager(viewPager);
    }

    private void bottomNavigation(){

         BottomBarUtils bottomBarUtils = new BottomBarUtils();
         int[] mIconSelectIds = bottomBarUtils.bottomSelectedIcons;
         int[] mIconUnSelectIds = bottomBarUtils.bottomUnSelectedIcons;
         ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
         String[] mTitles = bottomBarUtils.bottomTitles;

        CommonTabLayout tableLayoutHome;
        tableLayoutHome = findViewById(R.id.table_layout_home);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        tableLayoutHome.setTabData(mTabEntities);
        tableLayoutHome.setCurrentTab(0);

        tableLayoutHome.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {


                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, Reward.class));
                        overridePendingTransition(0,0);
                        break;
                    case 2:

                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        overridePendingTransition(0,0);

//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.frame, takeTest, "");
//                        fragmentTransaction.commit();
                        break;
                    case 3:

                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        overridePendingTransition(0,0);

                        break;
                    case 4:

                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        overridePendingTransition(0,0);

                        break;
                    default: break;
                }

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }




}