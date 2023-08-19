package com.humorstech.respyr.authentication.profile_creation.personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.utills.ProfileCreationData;
import com.shawnlin.numberpicker.NumberPicker;

import java.text.DecimalFormat;

public class Height extends AppCompatActivity {

    private Button buttonBack, buttonNext;

    private EditText etHeight;
    private RadioGroup rgHeightType;

    private boolean feetFlag=false;

    private double HeightSelected=0;

    private double centimeter =180.544;
    private int feet=5, inches=9;

    private NumberPicker feetPicker,inchesPicker,cmPicker;

    private TextView txtLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        StatusBarColor statusBarColor = new StatusBarColor(Height.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();

    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    private void init(){
        setReferences();
        onClicks();
        heightType();
        pickers();


    }
    private void setReferences(){
        buttonBack=findViewById(R.id.button_back);
        buttonNext=findViewById(R.id.button_next);
        etHeight=findViewById(R.id.et_height);
        txtLog=findViewById(R.id.txt_log);
        txtLog.setMovementMethod(new ScrollingMovementMethod());

        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 4,3);

    }
    private void onClicks(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println(HeightSelected);
                System.out.println(feetFlag);
                System.out.println(feet);
                System.out.println(inches);

                SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(ProfileCreationData.HEIGHT, String.valueOf(HeightSelected));
                editor.apply();

                Intent intent= new Intent(getApplicationContext(), Weight.class);
                startActivity(intent);


            }
        });
    }



    private void heightType(){
        rgHeightType=findViewById(R.id.rg_height_type);

        RadioButton rbFt = findViewById(R.id.rb_height_ft);
        RadioButton rbCm= findViewById(R.id.rb_height_cm);


        LinearLayout feetLayout =findViewById(R.id.feet_layout);
        LinearLayout inchesLayout =findViewById(R.id.inches_layout);

        rbCm.setChecked(true);
        rbCm.setTextColor(getColor(R.color.white));
        rbFt.setTextColor(getColor(R.color.grey));

        rgHeightType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_height_ft){
                    rbFt.setTextColor(getColor(R.color.white));
                    rbCm.setTextColor(getColor(R.color.grey));

                    if (!etHeight.getText().toString().isEmpty()){
                        double feetHeight = Double.parseDouble(etHeight.getText().toString());
                        etHeight.setText(String.valueOf(roundTwoDecimals(cmToFeet(feetHeight))));
                        etHeight.setSelection(etHeight.getText().length());
                    }
                    feetFlag=true;
                    feetLayout.setVisibility(View.VISIBLE);
                    inchesLayout.setVisibility(View.GONE);

                    updateCentimeterPicker();

                }else if(checkedId==R.id.rb_height_cm){
                    rbCm.setTextColor(getColor(R.color.white));
                    rbFt.setTextColor(getColor(R.color.grey));

                    if (!etHeight.getText().toString().isEmpty()){
                        double feetHeight = Double.parseDouble(etHeight.getText().toString());
                        etHeight.setText(String.valueOf(roundTwoDecimals(feetToCM(feetHeight))));
                        etHeight.setSelection(etHeight.getText().length());
                    }
                    feetFlag=false;

                    updateFeetInchPicker();
                    feetLayout.setVisibility(View.GONE);
                    inchesLayout.setVisibility(View.VISIBLE);



                }
            }
        });
    }

    private double cmToFeet(double cm){
        return  cm / 30.48;
    }

    private double feetToCM(double feet){
        return feet * 30.48;
    }
    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("##.##");
        return Double.parseDouble(twoDForm.format(d));
    }

    private void pickers(){

        HeightSelected = updateHeight(centimeter);

        feetPicker = findViewById(R.id.feet_picker);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_bold);
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.roboto);
        feetPicker.setSelectedTypeface(typeface);
        feetPicker.setTypeface(typeface2);
        feetPicker.setWrapSelectorWheel(false);


        inchesPicker = findViewById(R.id.inches_picker);
        inchesPicker.setSelectedTypeface(typeface);
        inchesPicker.setTypeface(typeface2);
        inchesPicker.setWrapSelectorWheel(false);


        cmPicker = findViewById(R.id.cm_picker);
        cmPicker.setSelectedTypeface(typeface);
        cmPicker.setTypeface(typeface2);
        cmPicker.setWrapSelectorWheel(false);



        // set default values
        feetPicker.setValue(feet);
        inchesPicker.setValue(inches);
        cmPicker.setValue((int)centimeter);
        HeightSelected=centimeter;


        ////////////////////////////////////// perform feet
        feetPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                // Format the value with commas
                return String.format("%,d'", value);
            }
        });
        feetPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                feet = newVal;
                updateCentimeterPicker();
            }
        });

        ////////////////////////////////////// perform inches

        inchesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                inches = newVal;
                updateCentimeterPicker();
            }
        });


        ////////////////////////////////////// perform centimeter

        cmPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int nwVal) {
                centimeter = nwVal;
                HeightSelected=centimeter;
            }
        });


    }

    private void updateFeetInchPicker(){
        String combinedValue = String.valueOf(roundTwoDecimals(cmToFeet(centimeter)));

        txtLog.append(combinedValue+"\n");

        String[] parts = combinedValue.split("\\.");

        int feet1 = (int)Double.parseDouble(parts[0]);
        int inches1 = (int)Double.parseDouble(parts[1]);


        txtLog.append(String.valueOf(feet1)+"\n");
        txtLog.append(String.valueOf(inches1)+"\n");

        feetPicker.setValue(feet1);
        inchesPicker.setValue(inches1);

    }
    private void updateCentimeterPicker(){
        String result = String.valueOf(feet) + "." + String.valueOf(inches);
        cmPicker.setValue((int) updateHeight(feetToCM(Double.parseDouble(result))));
        txtLog.append(String.valueOf((int) updateHeight(feetToCM(Double.parseDouble(result))))+"\n");
    }

    private double updateHeight(double heightValue){
        return roundTwoDecimals(heightValue);
    }

}