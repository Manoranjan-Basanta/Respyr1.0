package com.humorstech.respyr.reading;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import com.humorstech.respyr.R;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import java.util.Random;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class DiabeticAnalysis {
    public static  int unHealthy = 60;
    public static  int moderate = 80;
    public static  int healthy = 100;

    public static String[] status = {"Poor", "Step up a notch", "Fair"};
    public static String[] highRiskTitles = {"Attention Required, "};
    public static String[] highRiskSubtitles = {"Your diabetic risk level is high & might be a cause of concern. Give it your all! Modify your lifestyle to get back on track."};
    public static String[] optimalTitles = {"Step up a notch, "};
    public static String[] optimalSubtitles = {"Your diabetic risk level is moderate and needs some work. Go the extra mile! Modify your lifestyle to see better results."};
    public static String[] noRiskTitles = {"Keep it up, "};
    public static String[] noRiskSubtitles = {"Your diabetes risk level is low which is great! Let’s continue to track your lifestyle to maintain it."};

    public static void setDiabetic(Context context, double score, String profileName, CircularProgressIndicator roundedProgressBar,
                                   TextView txtScore, TextView txtRisk, TextView txtProfileName, TextView txtDiabeticSuggestion,TextView txtDiabeticMessage, ImageView imageView) {
        double roundedValue = Math.round(score);

        if (score - Math.floor(score) >= 0.5) {
            roundedValue = Math.ceil(score);
        }

        int scoreNew = (int) roundedValue;
        roundedProgressBar.setCurrentProgress(scoreNew);
        roundedProgressBar.setMaxProgress(100);


        String good = "which is <b><font color=\"#3FAF58\">" + "good" + "</font></b>";
        String fair = "which is <b><font color=\"#F8B10F\">" + "fair" + "</font></b>";
        String poor = "which is <b><font color=\"#EA5455\">" + "bad" + "</font></b>";



        if (score <= unHealthy) {
            roundedProgressBar.setProgressColor(ContextCompat.getColor(context, R.color.red));
            txtRisk.setText(status[0]);
            int randomIndex = getRandomIndex(highRiskTitles.length);

            setTitlesAndSubtitles(highRiskTitles[randomIndex], highRiskSubtitles[randomIndex],txtProfileName, txtDiabeticSuggestion, profileName);

            Spanned message = Html.fromHtml(poor);
            txtDiabeticMessage.setText(message);

            imageView.setImageResource(R.drawable.badge_poor);

        } else if (score <= moderate) {
            roundedProgressBar.setProgressColor(ContextCompat.getColor(context, R.color.yellow));
            txtRisk.setText(status[1]);
            int randomIndex = getRandomIndex(optimalTitles.length);
            setTitlesAndSubtitles(optimalTitles[randomIndex], optimalSubtitles[randomIndex],txtProfileName, txtDiabeticSuggestion,profileName);

            Spanned message = Html.fromHtml(fair);
            txtDiabeticMessage.setText(message);

            imageView.setImageResource(R.drawable.diabetic_badge);

        } else if (score > moderate && score <= healthy) {
            roundedProgressBar.setProgressColor(ContextCompat.getColor(context, R.color.green));
            txtRisk.setText(status[2]);
            int randomIndex = getRandomIndex(noRiskTitles.length);
            setTitlesAndSubtitles(noRiskTitles[randomIndex], noRiskSubtitles[randomIndex],txtProfileName, txtDiabeticSuggestion,profileName);

            Spanned message = Html.fromHtml(good);
            txtDiabeticMessage.setText(message);

            imageView.setImageResource(R.drawable.diabetic_badge);
        }



        txtScore.setText(String.valueOf(scoreNew));
    }

    private static int getRandomIndex(int length) {
        Random random = new Random();
        return random.nextInt(length);
    }

    @SuppressLint("SetTextI18n")
    private static void setTitlesAndSubtitles(String title, String subtitle, TextView txtTitle, TextView txtDescription , String profileName) {
        Log.d("DiabeticAnalysis", "Title: " + title );
        Log.d("DiabeticAnalysis", "Subtitle: " + subtitle);
        txtTitle.setText(title+" "+profileName + "!");
        txtDescription.setText(subtitle);
    }
}






