package com.humorstech.respyr.reading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.humorstech.respyr.R;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class BlowScoreAnalysis {



    public static  int unHealthy = 60;
    public static  int moderate = 80;
    public static  int healthy = 100;


    public static String[] status = {"Poor", "Step up a notch", "Fair"};
    public static String[] highRiskTitles = {"Attention Required, "};
    public static String[] highRiskSubtitles = {"Your respiratory risk level is high & might be a cause of concern. Give it your all! Modify your lifestyle to get back on track."};
    public static String[] optimalTitles = {"Step up a notch, "};
    public static String[] optimalSubtitles = {"Your respiratory risk level is moderate and needs some work. Go the extra mile! Modify your lifestyle to see better results."};
    public static String[] noRiskTitles = {"Keep it up, "};
    public static String[] noRiskSubtitles = {"Your respiratory risk level is low which is great! Let’s continue to track your lifestyle to maintain it."};

    @SuppressLint("SetTextI18n")
    public static void setProgress(Context context, double blowScore, String profileName, CircularProgressIndicator circularProgressIndicator,
                                   TextView txtBlowScore, TextView txtProfileNameFooter, TextView txtSuggestion, TextView blowMessage, ImageView imgBlowWarning){

        double roundedValue = Math.round(blowScore);

        if (blowScore - Math.floor(blowScore) >= 0.5) {
            roundedValue = Math.ceil(blowScore);
        }
        if (roundedValue>100){
            roundedValue=100;
        }

        int scoreNew = (int) roundedValue;

        circularProgressIndicator.setCurrentProgress(scoreNew);
        txtBlowScore.setText(String.valueOf(scoreNew));



        String good = "which is <b><font color=\"#3FAF58\">" + "good" + "</font></b>";
        String fair = "which is <b><font color=\"#F8B10F\">" + "fair" + "</font></b>";
        String poor = "which is <b><font color=\"#EA5455\">" + "poor" + "</font></b>";




        if(scoreNew<=unHealthy){
           // txtMessage.setText(titles[2]);
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.red));

            Spanned message = Html.fromHtml(poor);
            blowMessage.setText(message);

            txtSuggestion.setText(highRiskTitles[0] + profileName + "!");
            txtProfileNameFooter.setText(highRiskSubtitles[0]);


            imgBlowWarning.setImageResource(R.drawable.badge_poor);

        }else if (scoreNew<moderate){
           // txtMessage.setText(titles[1]);
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.yellow));

            Spanned message = Html.fromHtml(fair);
            blowMessage.setText(message);

            txtSuggestion.setText(optimalTitles[0] + profileName + "!");
            txtProfileNameFooter.setText(optimalSubtitles[0]);


            imgBlowWarning.setImageResource(R.drawable.diabetic_badge);
        }else if(scoreNew<=healthy){
          //  scoreNew.setText(titles[0]);
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.green));
            blowMessage.setText(status[2]);

            Spanned message = Html.fromHtml(good);
            blowMessage.setText(message);

            txtSuggestion.setText(noRiskTitles[0] + profileName + "!");
            txtProfileNameFooter.setText(noRiskSubtitles[0]);

            imgBlowWarning.setImageResource(R.drawable.diabetic_badge);
        }

    }
}
