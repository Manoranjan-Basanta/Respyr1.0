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

public class Lipid extends AppCompatActivity {


    /// Titles
    private TextView txtD1,txtD2,txtD3,txtD4,txtD5,txtD6,txtD7;


    // RadioGroups
    private RadioGroup rg1,rg2,rg3,rg4,rg5,rg6,rg7;
    private RadioGroup diseaseTab;

    // RadioButtons
    private RadioButton rb1,rb2,rb3, rb4, rb5, rb6, rb7,rb8,rb9,rb10,rb11,rb12,rb13,rb14,rb15,rb16,rb17,rb18,rb19,rb20,rb21;
    private RadioButton rbYes, rbNo;

    private Button buttonNext;

    private static final String TAG = "Lipid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lipid);
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
        d4();
        d5();
        d6();
        d7();

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
        txtD4=findViewById(R.id.txtd4);
        txtD5=findViewById(R.id.txtd5);
        txtD6=findViewById(R.id.txtd6);
        txtD7=findViewById(R.id.txtd7);

        // Radio Group
        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);
        rg3 = findViewById(R.id.rg3);
        rg4 = findViewById(R.id.rg4);
        rg5 = findViewById(R.id.rg5);
        rg6 = findViewById(R.id.rg6);
        rg7 = findViewById(R.id.rg7);

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
        rb10 = findViewById(R.id.rb10);
        rb11 = findViewById(R.id.rb11);
        rb12 = findViewById(R.id.rb12);
        rb13 = findViewById(R.id.rb13);
        rb14 = findViewById(R.id.rb14);
        rb15 = findViewById(R.id.rb15);
        rb16 = findViewById(R.id.rb16);
        rb17 = findViewById(R.id.rb17);
        rb19 = findViewById(R.id.rb19);
        rb20 = findViewById(R.id.rb20);
        rb21 = findViewById(R.id.rb21);

        // buttons
        buttonNext=findViewById(R.id.button_next);
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
                    Intent intent = new Intent(getApplicationContext(), Kidney.class);
                    startActivity(intent);
                }catch (Exception e){

                }
            }
        });
    }

    private void d1(){
        Spanned title = Html.fromHtml("<font color='#535359'>1.You’re </font> <font color='#308BF9'>Total Cholesterol  <br/>  range </font><font color='#535359'>is</font>");

        Spanned op1 = Html.fromHtml("<font> < 200 mg/dl </font>");
        Spanned op2 = Html.fromHtml("<font>between <br /> 200 - 239 mg/dl </font>");
        Spanned op3 = Html.fromHtml("<font>> 240 mg/dl </font>");

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


        Spanned title = Html.fromHtml("<font color='#535359'>2.You’re </font> <font color='#308BF9'> Triglycerides  <br/>  range </font><font color='#535359'>is</font>");

        Spanned op1 = Html.fromHtml("<font> < 150 mg/dl </font>");
        Spanned op2 = Html.fromHtml("<font>between <br/>150 - 199 mg/dl </font>");
        Spanned op3 = Html.fromHtml("<font> > 200 mg/dl </font>");

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

        Spanned title = Html.fromHtml("<font color='#535359'>3.You’re </font> <font color='#308BF9'> HDL Cholesterol  <br/>  range </font><font color='#535359'>is</font>");

        Spanned op1 = Html.fromHtml("<font> < 40 mg/dl </font>");
        Spanned op2 = Html.fromHtml("<font>between <br />40 - 60 mg/dl </font>");
        Spanned op3 = Html.fromHtml("<font> > 60 mg/dl </font>");

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
    private void d4(){
        Spanned title = Html.fromHtml("<font color='#535359'>4.You’re </font> <font color='#308BF9'> VLDL Cholesterol  <br/>  range </font><font color='#535359'>is</font>");

        Spanned op1 = Html.fromHtml("<font> < 2 mg/dl </font>");
        Spanned op2 = Html.fromHtml("<font> between  <br /> 2 - 30 mg/dl </font>");
        Spanned op3 = Html.fromHtml("<font> > 30 mg/dl </font>");

        txtD4.setText(title);
        rb10.setText(op1);
        rb11.setText(op2);
        rb12.setText(op3);

        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rb10){
                    rb10.setTextColor(getColor(R.color.white));
                    rb11.setTextColor(getColor(R.color.grey));
                    rb12.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb11){
                    rb11.setTextColor(getColor(R.color.white));
                    rb10.setTextColor(getColor(R.color.grey));
                    rb12.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb12){
                    rb12.setTextColor(getColor(R.color.white));
                    rb10.setTextColor(getColor(R.color.grey));
                    rb11.setTextColor(getColor(R.color.grey));
                }
            }
        });

    }

    private void d5(){

        Spanned title = Html.fromHtml("<font color='#535359'>5.You’re </font> <font color='#308BF9'> LDL Cholesterol <br/>  range </font><font color='#535359'>is</font>");

        Spanned op1 = Html.fromHtml("<font> < 100 mg/dl </font>");
        Spanned op2 = Html.fromHtml("<font> between <br /> 100 - 129 mg/dl </font>");
        Spanned op3 = Html.fromHtml("<font> > 129 mg/dl </font>");

        txtD5.setText(title);
        rb13.setText(op1);
        rb14.setText(op2);
        rb15.setText(op3);

        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rb13){
                    rb13.setTextColor(getColor(R.color.white));
                    rb14.setTextColor(getColor(R.color.grey));
                    rb15.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb14){
                    rb14.setTextColor(getColor(R.color.white));
                    rb13.setTextColor(getColor(R.color.grey));
                    rb15.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb15){
                    rb15.setTextColor(getColor(R.color.white));
                    rb13.setTextColor(getColor(R.color.grey));
                    rb14.setTextColor(getColor(R.color.grey));
                }
            }
        });

    }
    private void d6(){


        Spanned title = Html.fromHtml("<font color='#535359'>6.You’re </font> <font color='#308BF9'> Total CHOL / HDL  <br />Cholesterol Ratio </font><font color='#535359'>is</font>");

        Spanned op1 = Html.fromHtml("<font> < 4.5 mg/dl </font>");
        Spanned op2 = Html.fromHtml("<font> > 4.5 mg/dl </font>");


        txtD6.setText(title);
        rb16.setText(op1);
        rb17.setText(op2);


        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rb16){
                    rb16.setTextColor(getColor(R.color.white));
                    rb17.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb17){
                    rb17.setTextColor(getColor(R.color.white));
                    rb16.setTextColor(getColor(R.color.grey));
                }

            }
        });

    }
    private void d7(){

        Spanned title = Html.fromHtml("<font color='#535359'>7.You’re </font> <font color='#308BF9'> LDL / HDL <br /> Cholesterol Ratio </font><font color='#535359'>is</font>");

        Spanned op1 = Html.fromHtml("<font> < 3 mg/dl </font>");
        Spanned op2 = Html.fromHtml("<font> between <br /> 3 - 6 mg/dl </font>");
        Spanned op3 = Html.fromHtml("<font> > 6 mg/dl </font>");


        txtD7.setText(title);
        rb19.setText(op1);
        rb20.setText(op2);
        rb21.setText(op3);


        rg7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rb19){
                    rb19.setTextColor(getColor(R.color.white));
                    rb20.setTextColor(getColor(R.color.grey));
                    rb21.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb20){
                    rb20.setTextColor(getColor(R.color.white));
                    rb19.setTextColor(getColor(R.color.grey));
                    rb21.setTextColor(getColor(R.color.grey));
                }else if(checkedId==R.id.rb21){
                    rb21.setTextColor(getColor(R.color.white));
                    rb19.setTextColor(getColor(R.color.grey));
                    rb20.setTextColor(getColor(R.color.grey));
                }

            }
        });

    }
}