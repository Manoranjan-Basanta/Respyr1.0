package com.humorstech.respyr.reading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chinalwb.slidetoconfirmlib.ISlideListener;
import com.chinalwb.slidetoconfirmlib.SlideToConfirm;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;

public class CheckList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        StatusBarColor statusBarColor= new StatusBarColor(CheckList.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));


        init();


        final SlideToConfirm slideToConfirm = findViewById(R.id.slide_to_confirm_1);
        slideToConfirm.setSlideListener(new ISlideListener() {
            @Override
            public void onSlideStart() {
            }

            @Override
            public void onSlideMove(float percent) {
            }

            @Override
            public void onSlideCancel() {
            }

            @Override
            public void onSlideDone() {

                slideToConfirm.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), DeviceReady.class);
                        startActivity(intent);
                        finish();
                    }
                }, 500);
            }
        });


    }

    private void init(){
        setTextCustom();
        performCheckBox();
    }
    private void setTextCustom(){

        TextView txtCheckList1=findViewById(R.id.txt_checklist_1);
        TextView txtCheckList2=findViewById(R.id.txt_checklist_2);
        TextView txtCheckList3=findViewById(R.id.txt_checklist_3);
        TextView txtCheckList4=findViewById(R.id.txt_checklist_4);
        TextView txtCheckList5=findViewById(R.id.txt_checklist_5);
        TextView txtCheckList6=findViewById(R.id.txt_checklist_6);

        String checklist1 = "<b>I’m haven't eaten in the</b><font color=\"#308BF9\">" + " last four hours." + "</font> ";
        String checklist2 = "<b>I haven’t consumed water</b> or any other fluid in the <font color=\"#308BF9\"> last one hour.</font>";
        String checklist3 = "<b>I haven't consumed any alcohol</b> in<font color=\"#308BF9\">" + " last 24 hours." + "</font>";
        String checklist4 = "<b>I haven't smoked </b>or consuming any type of tobacco in <font color=\"#308BF9\">" + " last four hours. " + "</font>";
        String checklist5 = "<b>I haven’t brushed</b>my teeth in<font color=\"#308BF9\">" + " last one hour." + "</font>";
        String checklist6 = " I am <b>sitting relaxed</b> to take the test";


        Spanned newCheckList1 = Html.fromHtml(checklist1);
        Spanned newCheckList2 = Html.fromHtml(checklist2);
        Spanned newCheckList3 = Html.fromHtml(checklist3);
        Spanned newCheckList4 = Html.fromHtml(checklist4);
        Spanned newCheckList5 = Html.fromHtml(checklist5);
        Spanned newCheckList6 = Html.fromHtml(checklist6);


        txtCheckList1.setText(newCheckList1);
        txtCheckList2.setText(newCheckList2);
        txtCheckList3.setText(newCheckList3);
        txtCheckList4.setText(newCheckList4);
        txtCheckList5.setText(newCheckList5);
        txtCheckList6.setText(newCheckList6);

    }
    private void performCheckBox(){
        CheckBox chkSelectAll=findViewById(R.id.chk_select_all);
        CheckBox chk1=findViewById(R.id.chk_1);
        CheckBox chk2=findViewById(R.id.chk_2);
        CheckBox chk3=findViewById(R.id.chk_3);
        CheckBox chk4=findViewById(R.id.chk_4);
        CheckBox chk5=findViewById(R.id.chk_5);
        CheckBox chk6=findViewById(R.id.chk_6);

        chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    chk1.setChecked(true);
                    chk2.setChecked(true);
                    chk3.setChecked(true);
                    chk4.setChecked(true);
                    chk5.setChecked(true);
                    chk6.setChecked(true);
                }else{
                    chk1.setChecked(false);
                    chk2.setChecked(false);
                    chk3.setChecked(false);
                    chk4.setChecked(false);
                    chk5.setChecked(false);
                    chk6.setChecked(false);
                }
            }
        });

    }
}