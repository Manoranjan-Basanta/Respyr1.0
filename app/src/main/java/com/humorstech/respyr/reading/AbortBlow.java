package com.humorstech.respyr.reading;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.humorstech.respyr.Dashboard;
import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.NetworkErrorSheet;
import com.humorstech.respyr.R;
import com.humorstech.respyr.authentication.Welcome;

public class AbortBlow {

    static BottomSheetDialog bsDialog;


    public static void show(Context context, Activity activity) {
        if (context == null) {
            // Handle the case where the context is null
            return;
        }

        try {
            bsDialog = new BottomSheetDialog(context, R.style.TransparentDialog);
            bsDialog.setContentView(R.layout.abort_blow_sheet);
            bsDialog.getWindow().setNavigationBarColor(ContextCompat.getColor(context, R.color.white));

            bsDialog.show();
        } catch (WindowManager.BadTokenException e) {
            // Handle the BadTokenException
            e.printStackTrace();
        }
        Button buttonTryAgain =bsDialog.findViewById(R.id.button_try_again);

        Welcome welcome = new Welcome();
        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsDialog.dismiss();
                performNext(context, activity);
            }
        });


    }

    public static void performNext(Context context, Activity activity){
        Intent intent = new Intent(context, Dashboard.class);
        context.startActivity(intent);
        activity.finish();
    }
}
