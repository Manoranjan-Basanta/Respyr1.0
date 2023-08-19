package com.humorstech.respyr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.humorstech.respyr.authentication.Welcome;
import com.humorstech.respyr.authentication.login.Login;
import com.humorstech.respyr.daily_routine.DailyRoutinForm;
import com.humorstech.respyr.reading.BeforeReading;
import com.humorstech.respyr.reading.SelectUser;
import com.humorstech.respyr.utills.ActiveProfile;
import com.humorstech.respyr.utills.LoginUtils;
import com.humorstech.respyr.utills.ReadingParameters;

public class WhoTakingTestSheet {

    static BottomSheetDialog bsDialog;

    public static void show(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            // Handle the case when the activity is not valid or already finishing
            return;
        }

        try {
            bsDialog = new BottomSheetDialog(activity, R.style.TransparentDialog);
            bsDialog.setContentView(R.layout.layout_whos_reading);
            bsDialog.getWindow().setNavigationBarColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.white));

            Button buttSelf=bsDialog.findViewById(R.id.button_my_self);
            Button buttonFriend=bsDialog.findViewById(R.id.button_my_friend);
            ImageButton buttonBack=bsDialog.findViewById(R.id.button_back);

            buttSelf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performMySelf(activity);
                }
            });
            buttonFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performMyFriend(activity);
                }
            });
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bsDialog.dismiss();
                }
            });

            bsDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public static void performMySelf(Activity activity) {

        SharedPreferences sharedPreferences = activity.getApplicationContext().getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);

        String personal_information = sharedPreferences.getString(ActiveProfile.DATA_PERSONAL_INFORMATION, "");
        String habits_information = sharedPreferences.getString(ActiveProfile.DATA_HABITS_INFORMATION, "");

        SharedPreferences sharedPreferences2 = activity.getApplicationContext().getSharedPreferences(ReadingParameters.TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences2.edit();

        System.out.println("data-->"+personal_information);

        editor.putString(ReadingParameters.DATA_PERSONAL_INFORMATION, personal_information);
        editor.putString(ReadingParameters.DATA_HABITS_INFORMATION, habits_information);
        editor.apply();
        Intent intent = new Intent(activity.getApplicationContext(), DailyRoutinForm.class);
        intent.putExtra("data", 1);
        activity.startActivity(intent);


    }
    public static void performMyFriend( Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), SelectUser.class);
        activity.startActivity(intent);
    }

}