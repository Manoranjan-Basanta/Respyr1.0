package com.humorstech.respyr.authentication.profile_creation.medical_data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.humorstech.respyr.R;

public class Lungs extends AppCompatActivity {
    private RadioGroup diseaseTab;
    private RadioButton rbYes, rbNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lungs);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.dark1));
        init();
    }
    private void init(){
        setReferences();
        tabImplementation();
    }
    private void setReferences(){
        diseaseTab=findViewById(R.id.tab1);
        rbYes=findViewById(R.id.yes);
        rbNo=findViewById(R.id.no);
    }
    private void tabImplementation(){
        diseaseTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes) {
                    rbYes.setTextColor(getColor(R.color.white));
                    rbNo.setTextColor(getColor(R.color.black));
                }else if(checkedId == R.id.no){
                    rbNo.setTextColor(getColor(R.color.white));
                    rbYes.setTextColor(getColor(R.color.black));
                }
            }
        });
    }
}