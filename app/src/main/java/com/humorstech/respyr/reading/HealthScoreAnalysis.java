package com.humorstech.respyr.reading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.humorstech.respyr.R;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class HealthScoreAnalysis {

    public static  int unHealthy = 60;
    public static  int moderate = 80;
    public static  int healthy = 100;

    public static String[] titles = {"Good","Fair","Poor"};
    public static String[] subTitles = {
            ", Your score is great. Let’s continue to track your lifestyle to maintain it.",
            ", Your score needs work. Go the extra mile! Modify your lifestyle to see better results.",
            ", Your score requires attention and might be a cause of concern."};

    @SuppressLint("SetTextI18n")
    public static void setProgress(
            Context context,
            CircularProgressIndicator circularProgressIndicator,
            double healthScore,
                String profileName,
            TextView txtScore,
            TextView txtMessage,
            TextView txtSuggestion){


        double roundedValue = Math.round(healthScore);

        if (healthScore - Math.floor(healthScore) >= 0.5) {
            roundedValue = Math.ceil(healthScore);
        }

        int healthScoreNew = (int) roundedValue;

        txtScore.setText(String.valueOf(healthScoreNew));
        circularProgressIndicator.setCurrentProgress(healthScoreNew);
        circularProgressIndicator.setMaxProgress(healthScoreNew);

        if(healthScoreNew<=unHealthy){
            txtMessage.setText(titles[2]);
            txtSuggestion.setText(profileName + subTitles[2]);
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.red));
            txtMessage.setTextColor(context.getResources().getColor(R.color.red));
        }else if (healthScoreNew<moderate){
            txtMessage.setText(titles[1]);
            txtSuggestion.setText(profileName + subTitles[1]);
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.yellow));
            txtMessage.setTextColor(context.getResources().getColor(R.color.yellow));
        }else if(healthScoreNew<=healthy){
            txtMessage.setText(titles[0]);
            txtSuggestion.setText(profileName + subTitles[0]);
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.green));
            txtMessage.setTextColor(context.getResources().getColor(R.color.green));
        }
    }

}
