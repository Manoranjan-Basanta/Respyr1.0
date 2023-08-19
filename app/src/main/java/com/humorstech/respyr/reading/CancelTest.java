package com.humorstech.respyr.reading;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.humorstech.respyr.Dashboard;
import com.humorstech.respyr.R;
import com.humorstech.respyr.authentication.Welcome;

public class CancelTest {

    static BottomSheetDialog bsDialog;


    public static void show(Context context, Activity activity) {
        if (context == null) {
            // Handle the case where the context is null
            return;
        }

        try {
            bsDialog = new BottomSheetDialog(context, R.style.TransparentDialog);
            bsDialog.setContentView(R.layout.cancel_test_sheet);
            bsDialog.getWindow().setNavigationBarColor(ContextCompat.getColor(context, R.color.white));

            bsDialog.show();
        } catch (WindowManager.BadTokenException e) {
            // Handle the BadTokenException
            e.printStackTrace();
        }
        Button buttonCancel =bsDialog.findViewById(R.id.button_yes_cancel);
        Button buttonDoNotCancel =bsDialog.findViewById(R.id.button_do_not_cancel);

        Welcome welcome = new Welcome();
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsDialog.dismiss();
                performNext(context, activity);
            }
        });
        buttonDoNotCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsDialog.dismiss();
            }
        });


    }

    public static void performNext(Context context, Activity activity){
        Intent intent = new Intent(context, Dashboard.class);
        context.startActivity(intent);
        activity.finish();
    }
}
