package com.humorstech.respyr.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aries.ui.view.tab.CommonTabLayout;
import com.aries.ui.view.tab.listener.CustomTabEntity;
import com.aries.ui.view.tab.listener.OnTabSelectListener;
import com.humorstech.respyr.MainActivity;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.TabEntity;
import com.humorstech.respyr.TabUtils;

import java.util.ArrayList;

public class Main extends AppCompatActivity {


    TextView textView;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        StatusBarColor statusBarColor= new StatusBarColor(  Main.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));
        init();



    }
    private void init(){
        onClicks();
        setTab();
    }

    private void setTab(){
        ArrayList<CustomTabEntity> mTabEntities2 = new ArrayList<>();
        TabUtils tabUtils = new TabUtils();
        String[] tabTitles = tabUtils.notificationTabTitles;
        int[] tabSelectedIcons = tabUtils.notificationTabSelectedIcons;
        int[] tabUnSelectedIcons = tabUtils.notificationTabUnSelectedIcons;


        CommonTabLayout notificationTab = findViewById(R.id.notification_tab);
        FrameLayout frameNotification = findViewById(R.id.frame_notification);


        Alerts alerts = new Alerts();
        Promotional promotional = new Promotional();



        for (int i = 0; i < tabTitles.length; i++) {
            mTabEntities2.add(new TabEntity(tabTitles[i], tabSelectedIcons[i], tabUnSelectedIcons[i]));

        }
        notificationTab.setTabData(mTabEntities2);



        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_notification, alerts, "");
        fragmentTransaction.commit();



        notificationTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                switch (position){
                    case 0:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame_notification, alerts, "");
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame_notification, promotional, "");
                        fragmentTransaction.commit();
                        break;

                    default: break;
                }



            }

            @Override
            public void onTabReselect(int position) {

            }
        });


    }

    private void onClicks(){
        ImageButton buttonBack =findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}