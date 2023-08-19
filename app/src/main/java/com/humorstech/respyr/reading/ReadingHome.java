package com.humorstech.respyr.reading;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.humorstech.respyr.R;


public class ReadingHome extends AppCompatActivity {

    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_home);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        buttonNext =findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMouthTubeVideoDialog();
            }
        });


    }
    private void disableButton(){


        ColorStateList colorDisabled = ColorStateList.valueOf(getResources().getColor(R.color.disabled));
        ColorStateList colorPrimary = ColorStateList.valueOf(getResources().getColor(R.color.primary));

        int whiteColor = getResources().getColor(R.color.white);
        int disabledColor = getResources().getColor(R.color.grey);

        if (buttonNext.isEnabled()){

        }else{

        }


    }
    private void onConnectionChange(){
        TextView txtConnectionTitle = findViewById(R.id.txt_connection_status_title);
        TextView txtConnectionSubTitle = findViewById(R.id.txt_connection_status_sub_title);
    }
    private void showMouthTubeVideoDialog(){
        Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.dailog_reading1);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.flags = layoutParams.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            window.setAttributes(layoutParams);
        }

        dialog.show();
    }
}