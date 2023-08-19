package com.humorstech.respyr.authentication.profile_creation.medical_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.humorstech.respyr.R;

public class Kidney extends AppCompatActivity {

    private TextView txtD1, txtD2, txtD3;
    private RadioGroup rg1, rg2, rg3;
    private RadioButton rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8,rb9;
    private RadioButton rbYes,rbNo;
    private RadioGroup diseaseTab;

    private Button buttonNext;

    private static final String TAG = "Kidney";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kidney);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.dark1));

        init();
    }
    private void init(){
        initVars();
        tabImplementation();
        d1();
        d2();
        d3();

        onClick();

    }
    private void initVars(){
        // tab
        diseaseTab=findViewById(R.id.tab1);
        rbYes=findViewById(R.id.yes);
        rbNo=findViewById(R.id.no);

        // TextView
        txtD1=findViewById(R.id.txtd1);
        txtD2=findViewById(R.id.txtd2);
        txtD3=findViewById(R.id.txtd3);

        // Radio Groups
        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);
        rg3 = findViewById(R.id.rg3);

        // Radio Buttons
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        rb6 = findViewById(R.id.rb6);
        rb7 = findViewById(R.id.rb7);
        rb8 = findViewById(R.id.rb8);
        rb9 = findViewById(R.id.rb9);

        // Buttons
        buttonNext = findViewById(R.id.button_next);
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

    private void onClick(){
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              try {
                  Intent intent = new Intent(getApplicationContext(), Liver.class);
                  startActivity(intent);
              }catch (Exception e){

              }
            }
        });
    }
    private void d1(){
        Spanned title = Html.fromHtml("<font color='#535359'>1.You’re </font> <font color='#308BF9'>Serum Creatinine  <br/>&nbsp;&nbsp;&nbsp; range </font><font color='#535359'>is</font>");

        Spanned op1 = Html.fromHtml("<font> < 0.7 mg/dl </font>");
        Spanned op2 = Html.fromHtml("<font>  between <br /> 0.7 - 1.3 mg/dl </font>");
        Spanned op3 = Html.fromHtml("<font>> > 1.3 mg/dl </font>");

        txtD1.setText(title);
        rb1.setText(op1);
        rb2.setText(op2);
        rb3.setText(op3);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rb1){
                    rb1.setTextColor(getColor(R.color.white));
                    rb2.setTextColor(getColor(R.color.grey));
                    rb3.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb2){
                    rb2.setTextColor(getColor(R.color.white));
                    rb1.setTextColor(getColor(R.color.grey));
                    rb3.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb3){
                    rb3.setTextColor(getColor(R.color.white));
                    rb1.setTextColor(getColor(R.color.grey));
                    rb2.setTextColor(getColor(R.color.grey));
                }
            }
        });

    }
    private void d2(){

        Spanned title = Html.fromHtml("<font color='#535359'>2.You’re </font> <font color='#308BF9'> Serum Urea  <br/>&nbsp;&nbsp;&nbsp; range </font><font color='#535359'>is</font>");

        Spanned op1 = Html.fromHtml("<font> < 13 mg/dl </font>");
        Spanned op2 = Html.fromHtml("<font> between <br />13 - 48 mg/dl </font>");
        Spanned op3 = Html.fromHtml("<font> > 48 mg/dl </font>");

        txtD2.setText(title);
        rb4.setText(op1);
        rb5.setText(op2);
        rb6.setText(op3);

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rb4){
                    rb4.setTextColor(getColor(R.color.white));
                    rb5.setTextColor(getColor(R.color.grey));
                    rb6.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb5){
                    rb5.setTextColor(getColor(R.color.white));
                    rb4.setTextColor(getColor(R.color.grey));
                    rb6.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb6){
                    rb6.setTextColor(getColor(R.color.white));
                    rb4.setTextColor(getColor(R.color.grey));
                    rb5.setTextColor(getColor(R.color.grey));
                }
            }
        });

    }
    private void d3(){

        Spanned title = Html.fromHtml("<font color='#535359'>3.You’re </font> <font color='#308BF9'> Serum Uric Acid  <br/>&nbsp;&nbsp;&nbsp; range </font><font color='#535359'>is</font>");

        Spanned op1 = Html.fromHtml("<font> < 3.5 mg/dl </font>");
        Spanned op2 = Html.fromHtml("<font> between <br /> 3.5 - 7.2 mg/dl</font>");
        Spanned op3 = Html.fromHtml("<font> > 7.2 mg/dl </font>");

        txtD3.setText(title);
        rb7.setText(op1);
        rb8.setText(op2);
        rb9.setText(op3);

        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rb7){
                    rb7.setTextColor(getColor(R.color.white));
                    rb8.setTextColor(getColor(R.color.grey));
                    rb9.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb8){
                    rb8.setTextColor(getColor(R.color.white));
                    rb7.setTextColor(getColor(R.color.grey));
                    rb9.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb9){
                    rb9.setTextColor(getColor(R.color.white));
                    rb7.setTextColor(getColor(R.color.grey));
                    rb8.setTextColor(getColor(R.color.grey));
                }
            }
        });

    }
}