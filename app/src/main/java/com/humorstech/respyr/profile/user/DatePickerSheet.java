package com.humorstech.respyr.profile.user;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.humorstech.respyr.R;

import java.util.Date;

public class DatePickerSheet {
    Context context;
    Activity activity;
    private static final String TAG = "DisconnectedSheet";


    DatePickerSheet(Activity activity){
        this.activity = activity;
        this.context = activity;
    }

    Date date;

    public void show() {
        BottomSheetDialog bsDialog = new BottomSheetDialog(context, R.style.TransparentDialog);
        bsDialog.setContentView(R.layout.date_picker_dailog);
        bsDialog.getWindow().setNavigationBarColor(context.getColor(R.color.white));
        bsDialog.show();

        SingleDateAndTimePicker singleDateAndTimePicker= bsDialog.getWindow().findViewById(R.id.single_day_picker);

//        .displayMinutes(false)
//                .displayHours(false)
//                .displayDays(false)
//                .displayMonth(true)
//                .displayYears(true)
//                .displayDaysOfMonth(true)

        singleDateAndTimePicker.setDisplayMinutes(false);
        singleDateAndTimePicker.setDisplayHours(false);
        singleDateAndTimePicker.setDisplayDays(false);
        singleDateAndTimePicker.setDisplayMonths(true);
        singleDateAndTimePicker.setDisplayYears(true);
        singleDateAndTimePicker.setDisplayDaysOfMonth(true);
         date = singleDateAndTimePicker.getDate();

    }
    public Date getDate(){
        return  date;
    }

}
