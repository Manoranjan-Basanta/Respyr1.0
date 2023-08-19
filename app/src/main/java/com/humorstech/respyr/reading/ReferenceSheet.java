package com.humorstech.respyr.reading;


import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.humorstech.respyr.R;


public class ReferenceSheet {

    BottomSheetDialog bsDialog;
    Context context;

    ReferenceSheet(Context context) {
        this.context = context;
    }



    public void show() {
        if (context == null) {
            // Handle the case where the context is null
            return;
        }

        try {
            bsDialog = new BottomSheetDialog(context, R.style.TransparentDialog);
            bsDialog.setContentView(R.layout.sheet_reference);
            bsDialog.getWindow().setNavigationBarColor(ContextCompat.getColor(context, R.color.white));

            bsDialog.show();
        } catch (WindowManager.BadTokenException e) {
            // Handle the BadTokenException
            e.printStackTrace();
        }

        LinearLayout buttonCloseSheet =bsDialog.findViewById(R.id.button_close_sheet);
        buttonCloseSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsDialog.dismiss();
            }
        });
    }

}
