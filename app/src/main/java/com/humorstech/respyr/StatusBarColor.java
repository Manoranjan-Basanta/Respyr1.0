package com.humorstech.respyr;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

public class StatusBarColor {
    private Activity activity;
    private Window window;

    public StatusBarColor(Activity activity) {
        this.activity = activity;
        this.window = activity.getWindow();
    }

    public void setColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // For devices running Marshmallow or above
                window.setStatusBarColor(color);
            } else {
                // For devices running pre-Marshmallow
                window.setStatusBarColor(color);
            }
        }

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void setDarkColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            View decorView = window.getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // For devices running Marshmallow or above
                window.setStatusBarColor(color);
            } else {
                // For devices running pre-Marshmallow
                window.setStatusBarColor(color);
            }
        }
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
