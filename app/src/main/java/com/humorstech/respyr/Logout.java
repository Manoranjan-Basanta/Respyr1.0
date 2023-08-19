package com.humorstech.respyr;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.humorstech.respyr.authentication.Welcome;
import com.humorstech.respyr.authentication.login.Login;
import com.humorstech.respyr.utills.ActiveProfile;
import com.humorstech.respyr.utills.LoginUtils;

public class Logout {

    static BottomSheetDialog bsDialog;

    public static void show(Context context, Activity activity) {
        if (activity == null || activity.isFinishing()) {
            // Handle the case when the activity is not valid or already finishing
            return;
        }


        try {
            bsDialog = new BottomSheetDialog(activity, R.style.TransparentDialog);
            bsDialog.setContentView(R.layout.layout_logout_sheeet);
            bsDialog.getWindow().setNavigationBarColor(ContextCompat.getColor(context, R.color.white));
            bsDialog.show();
        } catch (WindowManager.BadTokenException e) {
            // Handle the BadTokenException
            e.printStackTrace();
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Button buttonCancel = bsDialog.findViewById(R.id.button_yes_cancel);
        Button buttonDoNotCancel = bsDialog.findViewById(R.id.button_do_not_cancel);

        Welcome welcome = new Welcome();
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsDialog.dismiss();
                performLogout(context, activity);
            }
        });

        buttonDoNotCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsDialog.dismiss();
            }
        });
    }

    public static void performLogout(Context context, Activity activity) {

        SharedPreferences preferences = context.getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        SharedPreferences preferences1 = context.getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences1.edit();
        editor1.clear();
        editor1.apply();

        Intent intent = new Intent(context, Login.class);
        activity.startActivity(intent);
        activity.finish();
    }
}


