package com.humorstech.respyr.profile.history;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.humorstech.respyr.R;

public class DeleteReportBottomSheet {

    Context context;
    DeleteReportBottomSheet(Context context){
        this.context = context;
    }

    public void showCancelBottomSheet() {

        BottomSheetDialog bsDialog = new BottomSheetDialog(context, R.style.TransparentDialog);
        bsDialog.setContentView(R.layout.delete_blood_report);
        bsDialog.getWindow().setNavigationBarColor(context.getColor(R.color.white));
        bsDialog.show();


    }
}
