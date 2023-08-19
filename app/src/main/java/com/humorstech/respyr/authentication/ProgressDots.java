package com.humorstech.respyr.authentication;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.humorstech.respyr.R;

public class ProgressDots {
    public static void generateDotIndicators(Context context, LinearLayout linearLayout, int dots, int current) {
        LinearLayout dotIndicatorContainer =linearLayout;
        dotIndicatorContainer.removeAllViews(); // Clear existing dots (if any)

        float dpValue = 10; // Replace with the desired dp value
        float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dpValue * scale + 0.5f);

        int dotSize = pixels;

        for (int i = 1; i <= dots; i++) {

            View dot = new View(context);
            dot.setBackgroundResource(R.drawable.eclips1);

            if(i <= current){
                dot.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.primary));
            }else{
                dot.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.light));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotSize, dotSize);
            params.setMargins(9, 0, 9, 0);
            dot.setLayoutParams(params);
            dotIndicatorContainer.addView(dot);
        }
    }
}
