package com.humorstech.respyr.reading;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.humorstech.respyr.R;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class VitalAnalysis {

    public static int highRisk = 70;
    public static int optimal = 80;
    public static int noRisk = 100;

    public static String[] status = {"Poor", "Step up a notch", "Fair"};
    public static String[] highRiskTitles = {"Attention Required, "};
    public static String[] highRiskSubtitles = {"Your vital risk level is high & might be a cause of concern. Give it your all! Modify your lifestyle to get back on track."};
    public static String[] optimalTitles = {"Step up a notch, "};
    public static String[] optimalSubtitles = {"Your vital risk level is moderate and needs some work. Go the extra mile! Modify your lifestyle to see better results."};
    public static String[] noRiskTitles = {"Keep it up, "};
    public static String[] noRiskSubtitles = {"Your vital risk level is low which is great! Let’s continue to track your lifestyle to maintain it."};


    public static void setProgress(Context context,
                                   double vitalScore,
                                   String profileName,
                                   CircularProgressIndicator circularProgressIndicator,
                                   TextView txtVitalScore,
                                   TextView txtFooterName,
                                   TextView txtVitalSuggestion,
                                   TextView txtVitalMessage,
                                   ImageView imgVitalWarning){



        String good = "which is <b><font color=\"#3FAF58\">" + "good" + "</font></b>";
        String fair = "which is <b><font color=\"#F8B10F\">" + "fair" + "</font></b>";
        String poor = "which is <b><font color=\"#EA5455\">" + "poor" + "</font></b>";


        double roundedValue = Math.round(vitalScore);

        if (vitalScore - Math.floor(vitalScore) >= 0.5) {
            roundedValue = Math.ceil(vitalScore);
        }
        int vitalScoreNew = (int) roundedValue;


        circularProgressIndicator.setMaxProgress(100);
        circularProgressIndicator.setCurrentProgress(vitalScoreNew);
        txtVitalScore.setText(String.valueOf(vitalScoreNew));




        if(vitalScoreNew<=highRisk){
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.red));


            Spanned message = Html.fromHtml(poor);
            txtVitalMessage.setText(message);


            txtFooterName.setText(highRiskTitles[0] + profileName + "!");
            txtVitalSuggestion.setText(highRiskSubtitles[0]);

            imgVitalWarning.setImageResource(R.drawable.badge_poor);


        }else if (vitalScoreNew<optimal){
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.yellow));

            Spanned message = Html.fromHtml(fair);
            txtVitalMessage.setText(message);

            txtFooterName.setText(optimalTitles[0] + profileName + "!");
            txtVitalSuggestion.setText(optimalSubtitles[0]);


            imgVitalWarning.setImageResource(R.drawable.diabetic_badge);

        }else if(vitalScoreNew<=noRisk){
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.green));

            Spanned message = Html.fromHtml(good);
            txtVitalMessage.setText(message);

            txtFooterName.setText(noRiskTitles[0] + profileName + "!");
            txtVitalSuggestion.setText(noRiskSubtitles[0]);


            imgVitalWarning.setImageResource(R.drawable.diabetic_badge);
        }


    }
}
