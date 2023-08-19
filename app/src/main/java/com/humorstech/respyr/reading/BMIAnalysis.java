package com.humorstech.respyr.reading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.humorstech.respyr.R;

import java.text.DecimalFormat;

public class BMIAnalysis {

    public static double bmiUnderWeight = 18.5;
    public static double bmiNormal = 24.5;
    public static double bmiOverweight = 29.5;
    public static double bmiObese = 39.5;
    public static double bmiMorbidlyObese = 40;

    @SuppressLint("UseCompatLoadingForDrawables")
    public static void performBMIOperations(Context context, double bmi, TextView bmiValue, TextView bmiMeaning, ImageView BMIImage){


        DecimalFormat decimalFormat = new DecimalFormat("00.0");
        String bmiValueString = decimalFormat.format(bmi);
        bmiValue.setText(String.valueOf(bmiValueString));


        if (bmi <= bmiUnderWeight){

            bmiMeaning.setText("Under Weight");
            bmiMeaning.setBackgroundTintList(context.getResources().getColorStateList(R.color.under_weight));


            BMIImage.setImageResource(R.drawable.bmi1);

        }else if (bmi <= bmiNormal){

            bmiMeaning.setText("Normal");
            bmiMeaning.setBackgroundTintList(context.getResources().getColorStateList(R.color.normal));

            BMIImage.setImageResource(R.drawable.bmi2);

        }else if(bmi <= bmiOverweight){

            bmiMeaning.setText("Over weight");
            bmiMeaning.setBackgroundTintList(context.getResources().getColorStateList(R.color.overweight));

            BMIImage.setImageResource(R.drawable.bmi3);

        }else if (bmi <= bmiObese){

            bmiMeaning.setText("Obese");
            bmiMeaning.setBackgroundTintList(context.getResources().getColorStateList(R.color.obese));

            BMIImage.setImageResource(R.drawable.bmi4);

        }else if (bmi >= bmiMorbidlyObese){

            bmiMeaning.setText("Morbid Obese");
            bmiMeaning.setBackgroundTintList(context.getResources().getColorStateList(R.color.morbidly_obese));

            BMIImage.setImageResource(R.drawable.bmi4);
        }


    }
}
