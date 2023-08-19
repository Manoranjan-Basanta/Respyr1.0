package com.humorstech.respyr.reading;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.humorstech.respyr.R;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class LifeStyleAnalysis {
    public static  int unHealthy = 60;
    public static  int moderate = 80;
    public static  int healthy = 100;


    public static String[] titles = {"Good","Fair","Poor"};


    public static String[] status = {"Poor", "Step up a notch", "Fair"};
    public static String[] highRiskTitles = {"Start moving, "};
    public static String[] highRiskSubtitles = {"Take a step towards better health. You will not regret it. Make amends and track your progress to see the change."};
    public static String[] optimalTitles = {"Keep pushing, "};
    public static String[] optimalSubtitles = {"Your doing a fair job. Step up your game to see better results."};
    public static String[] noRiskTitles = {"You are on fire, "};
    public static String[] noRiskSubtitles = {"Your doing a fair job. Step up your game to see better results."};




    public static void setLifeStyleScore(
            Context context,
            double lifeStyleScore,
            CircularProgressIndicator circularProgressIndicator,
            TextView txtLifeStyleScore,
            TextView txtLifeStyleMessage,
            TextView txtLifeStyleFooterName,
            TextView txtLifeStyleFooterMessage,
            String profileName,
            ImageView imgBadge
    ){


        String good = "which is <b><font color=\"#3FAF58\">" + "good" + "</font></b>";
        String fair = "which is <b><font color=\"#F8B10F\">" + "fair" + "</font></b>";
        String poor = "which is <b><font color=\"#EA5455\">" + "poor" + "</font></b>";


        double roundedValue = Math.round(lifeStyleScore);

        if (lifeStyleScore - Math.floor(lifeStyleScore) >= 0.5) {
            roundedValue = Math.ceil(lifeStyleScore);
        }
        int lifeStyleScoreNew = (int) roundedValue;

        txtLifeStyleScore.setText(String.valueOf(lifeStyleScoreNew));

        circularProgressIndicator.setMaxProgress(100);
        circularProgressIndicator.setCurrentProgress(lifeStyleScoreNew);

        if(lifeStyleScoreNew<=unHealthy){
            // txtMessage.setText(titles[2]);
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.red));

            Spanned message = Html.fromHtml(poor);
            txtLifeStyleMessage.setText(message);

            txtLifeStyleFooterName.setText(highRiskTitles[0] + profileName + "!");
            txtLifeStyleFooterMessage.setText(highRiskSubtitles[0]);

            imgBadge.setImageResource(R.drawable.badge_poor);

        }else if (lifeStyleScoreNew<moderate){
            //    txtMessage.setText(titles[1]);
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.yellow));


            Spanned message = Html.fromHtml(fair);
            txtLifeStyleMessage.setText(message);

            txtLifeStyleFooterName.setText(optimalTitles[0] + profileName + "!");
            txtLifeStyleFooterMessage.setText(optimalSubtitles[0]);

            imgBadge.setImageResource(R.drawable.diabetic_badge);

        }else if(lifeStyleScoreNew<=healthy){
            //  txtMessage.setText(titles[0]);
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.green));


            Spanned message = Html.fromHtml(good);
            txtLifeStyleMessage.setText(message);

            txtLifeStyleFooterName.setText(noRiskTitles[0] + profileName + "!");
            txtLifeStyleFooterMessage.setText(noRiskSubtitles[0]);

            imgBadge.setImageResource(R.drawable.diabetic_badge);

        }


        int rating=Math.round(lifeStyleScoreNew / 20.0f);

//        switch (rating){
//            case 1 : radioButton1.setChecked(true); radioButton2.setChecked(false); radioButton3.setChecked(false); radioButton4.setChecked(false); radioButton5.setChecked(false); break;
//            case 2 : radioButton1.setChecked(true); radioButton2.setChecked(true); radioButton3.setChecked(false); radioButton4.setChecked(false); radioButton5.setChecked(false); break;
//            case 3 : radioButton1.setChecked(true); radioButton2.setChecked(true); radioButton3.setChecked(true); radioButton4.setChecked(false); radioButton5.setChecked(false); break;
//            case 4 : radioButton1.setChecked(true); radioButton2.setChecked(true); radioButton3.setChecked(true); radioButton4.setChecked(true); radioButton5.setChecked(false); break;
//            case 5 : radioButton1.setChecked(true); radioButton2.setChecked(true); radioButton3.setChecked(true); radioButton4.setChecked(true); radioButton5.setChecked(true); break;
//        }

    }

    public static void setNutrition(Context context,
                                    double nutritionScore,
                                    TextView txtNutritionScore,
                                    CircularProgressIndicator circularProgressIndicator,
                                    String profileName,
                                    TextView nutritionMessage,
                                    TextView txtFooterName,
                                    TextView txtFooterMessage,
                                    ImageView imgBadge){



        String[] status = {"Poor", "Step up a notch", "Fair"};
        String[] highRiskTitles = {"Focus on food, "};
        String[] highRiskSubtitles = {"Your nutrition needs attention. Make amends and track your progress to see the change."};
        String[] optimalTitles = {"Eat better, "};
        String[] optimalSubtitles = {"Your doing a fair job. Start tracking your nutrition to see better results."};
        String[] noRiskTitles = {"Fueling up right, "};
        String[] noRiskSubtitles = {"Your nutrition level is exceedingly well. Don’t stop now. Keep at it to enhance your health score."};




        String good = "which is <b><font color=\"#3FAF58\">" + "good" + "</font></b>";
        String fair = "which is <b><font color=\"#F8B10F\">" + "fair" + "</font></b>";
        String poor = "which is <b><font color=\"#EA5455\">" + "poor" + "</font></b>";



        double roundedValue = Math.round(nutritionScore);

        if (nutritionScore - Math.floor(nutritionScore) >= 0.5) {
            roundedValue = Math.ceil(nutritionScore);
        }
        int nutritionScoreNew = (int) roundedValue;

        txtNutritionScore.setText(String.valueOf(nutritionScoreNew));

        circularProgressIndicator.setMaxProgress(100);
        circularProgressIndicator.setCurrentProgress(nutritionScoreNew);


        if(nutritionScoreNew<=unHealthy){
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.red));

            Spanned message = Html.fromHtml(poor);
            nutritionMessage.setText(message);


            txtFooterName.setText(highRiskTitles[0] + profileName + "!");
            txtFooterMessage.setText(highRiskSubtitles[0]);

            imgBadge.setImageResource(R.drawable.badge_poor);

        }else if (nutritionScoreNew<moderate){
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.yellow));

            Spanned message = Html.fromHtml(fair);
            nutritionMessage.setText(message);

            txtFooterName.setText(optimalTitles[0] + profileName + "!");
            txtFooterMessage.setText(optimalSubtitles[0]);

            imgBadge.setImageResource(R.drawable.diabetic_badge);

        }else if(nutritionScoreNew<=healthy){
            circularProgressIndicator.setProgressColor(context.getResources().getColor(R.color.green));

            Spanned message = Html.fromHtml(good);
            nutritionMessage.setText(message);


            txtFooterName.setText(noRiskTitles[0] + profileName + "!");
            txtFooterMessage.setText(noRiskSubtitles[0]);


            imgBadge.setImageResource(R.drawable.diabetic_badge);
        }

    }
}
