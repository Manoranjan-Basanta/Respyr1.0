package com.humorstech.respyr;

import android.app.ProgressDialog;
import android.content.Context;

public class Dialogs {
    static ProgressDialog progressDialog;

    public static void showLoadingDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public static void hideLoadingDialog() {
        progressDialog.dismiss();
    }
}
