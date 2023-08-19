package com.humorstech.respyr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.humorstech.respyr.authentication.Welcome;
import com.humorstech.respyr.authentication.login.Login;

public class NetworkErrorSheet {


    static BottomSheetDialog bsDialog;


    public static void show(Context context, Activity activity, Class nextIntent) {
        if (context == null) {
            // Handle the case where the context is null
            return;
        }

        try {
            bsDialog = new BottomSheetDialog(context, R.style.TransparentDialog);
            bsDialog.setContentView(R.layout.network_error_sheet);
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
                performNext(context, activity, nextIntent);
            }
        });


    }

    public static void performNext(Context context, Activity activity, Class nextIntent){
        Dialogs.showLoadingDialog(context, "Loading");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialogs.hideLoadingDialog();
                if (isNetworkAvailable(context)) {

                     move(context,activity,nextIntent);

                }else{
                    NetworkErrorSheet.show(context,activity, nextIntent);
                }
            }
        },1500);
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
    public static void move(Context context,Activity activity, Class nextIntent){
        try {
            Intent intent = new Intent(context, nextIntent);
            context.startActivity(intent);
            activity.finish();
        }catch (Exception e){
        }
    }

}
