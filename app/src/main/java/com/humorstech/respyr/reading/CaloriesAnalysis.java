package com.humorstech.respyr.reading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.humorstech.respyr.R;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class CaloriesAnalysis {



    @SuppressLint("SetTextI18n")
    public static void performTexts(Context context,
                                    double score,
                                    String profileName,
                                    double actual,
                                    double recommended,
                                    TextView txtScore,
                                    TextView txtActual,
                                    TextView txtRecommended,
                                    TextView txtDifference,
                                    int i
    ){



        double roundedValue = Math.round(score);

        if (score - Math.floor(score) >= 0.5) {
            roundedValue = Math.ceil(score);
        }
        if (roundedValue>100){
            roundedValue=100;
        }

        int scoreNew = (int) roundedValue;

        txtScore.setText(String.valueOf(scoreNew)+"%");
        int difference = (int) ((int)recommended-actual);


        String type = "Calories";
        String type2 = "Calories";
        switch (i){
            case 1 :type="(kcal) Calories";type2=" (kcal)";break;
            case 2 :type="(g) Carbohydrate";type2=" (g)";break;
            case 3 :type="(g) Proteins";type2=" (g)";break;
            case 4 : type="(g) Fats";type2=" (g)";break;
            case 5 : type="(g) Fibre";type2=" (g)";break;
        }




        String differenceMessage;
        if (difference<0){
            int positiveI = Math.abs(difference);
            differenceMessage= profileName+", Your food intake exceeds "+String.valueOf(positiveI)+type;
        }else if(difference==0){
            differenceMessage= profileName+",Your food intake matches the recommended calorie intake of "+String.valueOf((int)recommended)+type;
        }
        else{
            differenceMessage= profileName+", Your food intake lacks "+String.valueOf((int)difference)+type;
        }

        txtDifference.setText(differenceMessage);
        txtRecommended.setText("/ "+String.valueOf((int)recommended) + type2);
        txtActual.setText(String.valueOf((int)actual)+ type2);

    }


    public static void performButtons(Context context, int i, TextView button1, TextView button2, TextView button3, TextView button4, TextView button5){
        switch (i){
            case 1:
                ColorStateList colorStateList = ColorStateList.valueOf(context.getResources().getColor(R.color.calories));
                button1.setBackgroundTintList(colorStateList);
                button1.setBackground(context.getResources().getDrawable(R.drawable.result_calories_button_shape));
                button1.setTextColor(context.getResources().getColor(R.color.white));

                button2.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button2.setTextColor(context.getResources().getColor(R.color.black));

                button3.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button3.setTextColor(context.getResources().getColor(R.color.black));

                button4.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button4.setTextColor(context.getResources().getColor(R.color.black));

                button5.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button5.setTextColor(context.getResources().getColor(R.color.black));

                break;
            case 2:
                ColorStateList colorStateList2 = ColorStateList.valueOf(context.getResources().getColor(R.color.carbohydrate));
                button2.setBackgroundTintList(colorStateList2);
                button2.setBackground(context.getResources().getDrawable(R.drawable.result_calories_button_shape));
                button2.setTextColor(context.getResources().getColor(R.color.white));

                button1.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button1.setTextColor(context.getResources().getColor(R.color.black));

                button3.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button3.setTextColor(context.getResources().getColor(R.color.black));

                button4.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button4.setTextColor(context.getResources().getColor(R.color.black));

                button5.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button5.setTextColor(context.getResources().getColor(R.color.black));

                break;
            case 3:
                ColorStateList colorStateList3 = ColorStateList.valueOf(context.getResources().getColor(R.color.protein));
                button3.setBackgroundTintList(colorStateList3);
                button3.setBackground(context.getResources().getDrawable(R.drawable.result_calories_button_shape));
                button3.setTextColor(context.getResources().getColor(R.color.white));

                button1.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button1.setTextColor(context.getResources().getColor(R.color.black));

                button2.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button2.setTextColor(context.getResources().getColor(R.color.black));

                button4.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button4.setTextColor(context.getResources().getColor(R.color.black));

                button5.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button5.setTextColor(context.getResources().getColor(R.color.black));

                break;
            case 4:
                ColorStateList colorStateList4 = ColorStateList.valueOf(context.getResources().getColor(R.color.fats));
                button4.setBackground(context.getResources().getDrawable(R.drawable.result_calories_button_shape));
                button4.setBackgroundTintList(colorStateList4);
                button4.setTextColor(context.getResources().getColor(R.color.white));

                button1.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button1.setTextColor(context.getResources().getColor(R.color.black));

                button2.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button2.setTextColor(context.getResources().getColor(R.color.black));

                button3.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button3.setTextColor(context.getResources().getColor(R.color.black));

                button5.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button5.setTextColor(context.getResources().getColor(R.color.black));
                break;
            case 5:
                ColorStateList colorStateList5 = ColorStateList.valueOf(context.getResources().getColor(R.color.fiber));
                button5.setBackground(context.getResources().getDrawable(R.drawable.result_calories_button_shape));
                button5.setBackgroundTintList(colorStateList5);
                button5.setTextColor(context.getResources().getColor(R.color.white));

                button1.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button1.setTextColor(context.getResources().getColor(R.color.black));

                button2.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button2.setTextColor(context.getResources().getColor(R.color.black));

                button3.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button3.setTextColor(context.getResources().getColor(R.color.black));

                button4.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                button4.setTextColor(context.getResources().getColor(R.color.black));
                break;
        }
    }

    public static void performLayouts(Context context, int i, RelativeLayout relativeLayout1,RelativeLayout relativeLayout2,RelativeLayout relativeLayout3,RelativeLayout relativeLayout4,RelativeLayout relativeLayout5 ){

        TranslateAnimation animation1 = setAnimation(relativeLayout1);
        TranslateAnimation animation2 = setAnimation(relativeLayout2);
        TranslateAnimation animation3 = setAnimation(relativeLayout3);
        TranslateAnimation animation4 = setAnimation(relativeLayout4);
        TranslateAnimation animation5 = setAnimation(relativeLayout5);



        switch (i){
            case 1 :
                relativeLayout1.setVisibility(View.VISIBLE);
                animation1.start();
                relativeLayout2.setVisibility(View.GONE);
                relativeLayout3.setVisibility(View.GONE);
                relativeLayout4.setVisibility(View.GONE);
                relativeLayout5.setVisibility(View.GONE);
                break;
            case 2 :
                relativeLayout2.setVisibility(View.VISIBLE);
                animation2.start();
                relativeLayout1.setVisibility(View.GONE);
                relativeLayout3.setVisibility(View.GONE);
                relativeLayout4.setVisibility(View.GONE);
                relativeLayout5.setVisibility(View.GONE);
                break;
            case 3 :
                relativeLayout3.setVisibility(View.VISIBLE);
                animation3.start();
                relativeLayout1.setVisibility(View.GONE);
                relativeLayout2.setVisibility(View.GONE);
                relativeLayout4.setVisibility(View.GONE);
                relativeLayout5.setVisibility(View.GONE);
                break;
            case 4 :
                relativeLayout4.setVisibility(View.VISIBLE);
                animation4.start();
                relativeLayout1.setVisibility(View.GONE);
                relativeLayout2.setVisibility(View.GONE);
                relativeLayout3.setVisibility(View.GONE);
                relativeLayout5.setVisibility(View.GONE);
                break;
            case 5 :
                relativeLayout5.setVisibility(View.VISIBLE);
                animation5.start();
                relativeLayout1.setVisibility(View.GONE);
                relativeLayout2.setVisibility(View.GONE);
                relativeLayout3.setVisibility(View.GONE);
                relativeLayout4.setVisibility(View.GONE);
                break;
        }
    }

    private static TranslateAnimation setAnimation(RelativeLayout layout){

        int width = layout.getWidth();

        TranslateAnimation animation = new TranslateAnimation(width, 0, 0, 0);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        animation.setDuration(500);

        return  animation;

    }

    public static void setProgress(
            Context context,
            int i,
            int progress1,
            int progress2,
            int progress3,
            int progress4,
            int progress5,
            CircularProgressIndicator p1,
            CircularProgressIndicator p2,
            CircularProgressIndicator p3,
            CircularProgressIndicator p4,
            CircularProgressIndicator p5){


        p1.setCurrentProgress(progress1 >> 1);
        p2.setCurrentProgress(progress2 >> 1);
        p3.setCurrentProgress(progress3 >> 1);
        p4.setCurrentProgress(progress4 >> 1);
        p5.setCurrentProgress(progress5 >> 1);

        p1.setMaxProgress(100);
        p2.setMaxProgress(100);
        p3.setMaxProgress(100);
        p4.setMaxProgress(100);
        p5.setMaxProgress(100);

        if (i==1){
            p1.setProgressColor(context.getResources().getColor(R.color.calories));
            p1.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_active));

            p2.setProgressColor(context.getResources().getColor(R.color.carbohydrate_inactive));
            p2.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p3.setProgressColor(context.getResources().getColor(R.color.protein_inactive));
            p3.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p4.setProgressColor(context.getResources().getColor(R.color.fats_inactive));
            p4.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p5.setProgressColor(context.getResources().getColor(R.color.fiber_inactive));
            p5.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

        }
        else if (i==2){
            p1.setProgressColor(context.getResources().getColor(R.color.calories_inactive));
            p1.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p2.setProgressColor(context.getResources().getColor(R.color.carbohydrate));
            p2.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_active));

            p3.setProgressColor(context.getResources().getColor(R.color.protein_inactive));
            p3.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p4.setProgressColor(context.getResources().getColor(R.color.fats_inactive));
            p4.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p5.setProgressColor(context.getResources().getColor(R.color.fiber_inactive));
            p5.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));
        }
        else if (i==3){
            p1.setProgressColor(context.getResources().getColor(R.color.calories_inactive));
            p1.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p2.setProgressColor(context.getResources().getColor(R.color.carbohydrate_inactive));
            p2.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p3.setProgressColor(context.getResources().getColor(R.color.protein));
            p3.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_active));

            p4.setProgressColor(context.getResources().getColor(R.color.fats_inactive));
            p4.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p5.setProgressColor(context.getResources().getColor(R.color.fiber_inactive));
            p5.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));
        }
        else if (i==4){
            p1.setProgressColor(context.getResources().getColor(R.color.calories_inactive));
            p1.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p2.setProgressColor(context.getResources().getColor(R.color.carbohydrate_inactive));
            p2.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p3.setProgressColor(context.getResources().getColor(R.color.protein_inactive));
            p3.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p4.setProgressColor(context.getResources().getColor(R.color.fats));
            p4.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_active));

            p5.setProgressColor(context.getResources().getColor(R.color.fiber_inactive));
            p5.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

        }
        else if (i==5){
            p1.setProgressColor(context.getResources().getColor(R.color.calories_inactive));
            p1.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p2.setProgressColor(context.getResources().getColor(R.color.carbohydrate_inactive));
            p2.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p3.setProgressColor(context.getResources().getColor(R.color.protein_inactive));
            p3.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p4.setProgressColor(context.getResources().getColor(R.color.fats_inactive));
            p4.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_in_active));

            p5.setProgressColor(context.getResources().getColor(R.color.fiber));
            p5.setProgressBackgroundColor(context.getResources().getColor(R.color.calories_progress_bg_active));
        }

    }
}
