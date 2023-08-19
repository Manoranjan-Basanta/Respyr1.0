package com.humorstech.respyr.reading;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.humorstech.respyr.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import com.humorstech.respyr.StatusBarColor;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import me.relex.circleindicator.CircleIndicator2;


public class Tempdashboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutSliderAdapter adapter;

    Map<String, Object> bmiData = new HashMap<>();

    private static final String TAG = "Result";
    double blow;

    // Declare the variables
    String response;
    String SP;
    String name;
    String DP;
    String Beats;
    String Breath;
    String spo2;
    String bpm1;
    String bpm2;
    String waterIntake;
    String smokingUnits;
    String isTakenAlcohol;
    String sleepHours;
    String exerciseInMinutes;
    String foodName;
    String foodQuantity;
    String foodCount;
    String isHadBreakfast;
    String isHadLunch;
    String isHadDinner;
    String USER_ID;
    String gender;
    String age;
    String height;
    String weight;


    private double healthScore;
    private double diabeticScore;
    private double vitalScore;
    private double bmi;
    private double bmr;
    private double curr_cal;
    private double curr_car;
    private double curr_pro;
    private double curr_fat;
    private double curr_fib;
    private double reco_cal;
    private double reco_car;
    private double reco_pro;
    private double reco_fat;
    private double reco_fib;
    private double OverallLifeStyleScore;
    private double detailedLifeStyleScore_lfstyle_score;
    private double detailed_nutrition_score;
    private String BMISuggestions;



    private TextView txtLog;

    double blowScore;
    ScrollView scrollView;
    final int YLifeStyle = 8129;
    ReferenceSheet referenceSheet = new ReferenceSheet(Tempdashboard.this);

    String date , time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempdashboard);

        StatusBarColor statusBarColor= new StatusBarColor(  Tempdashboard.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));


        // Get the SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("result_data", Context.MODE_PRIVATE);
        // Retrieve the data using the keys
        healthScore = Double.parseDouble(sharedPreferences.getString("health_score", "0"));
        diabeticScore = Double.parseDouble(sharedPreferences.getString("diabetic_score", "0"));
        vitalScore = Double.parseDouble(sharedPreferences.getString("vital_score", "0"));
        bmi = Double.parseDouble(sharedPreferences.getString("bmi", "0"));
        bmr = Double.parseDouble(sharedPreferences.getString("bmr", "0"));
        curr_cal = Double.parseDouble(sharedPreferences.getString("curr_cal", "0"));
        curr_car = Double.parseDouble(sharedPreferences.getString("curr_car", "0"));
        curr_pro = Double.parseDouble(sharedPreferences.getString("curr_pro", "0"));
        curr_fat = Double.parseDouble(sharedPreferences.getString("curr_fat", "0"));
        curr_fib = Double.parseDouble(sharedPreferences.getString("curr_fib", "0"));
        reco_cal = Double.parseDouble(sharedPreferences.getString("reco_cal", "0"));
        reco_car = Double.parseDouble(sharedPreferences.getString("reco_car", "0"));
        reco_pro = Double.parseDouble(sharedPreferences.getString("reco_pro", "0"));
        reco_fat = Double.parseDouble(sharedPreferences.getString("reco_fat", "0"));
        reco_fib = Double.parseDouble(sharedPreferences.getString("reco_fib", "0"));
        blowScore = Double.parseDouble(sharedPreferences.getString("blowScore", "0"));
        OverallLifeStyleScore = Double.parseDouble(sharedPreferences.getString("overall_lifestyle_score", "0"));
        detailed_nutrition_score = Double.parseDouble(sharedPreferences.getString("OverallNutrientScore", "0"));
        name = sharedPreferences.getString("name", "");
        BMISuggestions = sharedPreferences.getString("BMISuggestions", "null");
        response = sharedPreferences.getString("response", "null");
        date = sharedPreferences.getString("curr_date", "null");
        time = sharedPreferences.getString("curr_time", "null");





        scrollView = findViewById(R.id.scroll_dashboard);

        init();

        scrollFunction();



        LinearLayout linearLayout =findViewById(R.id.layout_start);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectUser.class);
                startActivity(intent);
            }
        });
    }
    private void scrollFunction(){


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        ImageView imageSwipeUp =findViewById(R.id.image_swipe_up);

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Handle scroll change event here
                // scrollX and scrollY represent the updated scroll position
                // oldScrollX and oldScrollY represent the previous scroll position
                int scrolledHeight = scrollView.getScrollY();

                System.out.println(scrolledHeight);
                if (scrolledHeight > 750){
                    imageSwipeUp.setVisibility(View.GONE);
                }else{
                    imageSwipeUp.setVisibility(View.VISIBLE);
                }
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // Scroll position has changed
                int scrollY = scrollView.getScrollY(); // Current vertical scroll position
                System.out.println(scrollY);
                //7512


            }
        });


        int i=0;
        Intent intent = getIntent();
        if (intent!=null){
            i = intent.getIntExtra("scroll", 0);
        }


        final int j =i;
        final int targetSectionId0 = R.id.main;
        final int targetSectionId1 = R.id.card_diabetic;
        final int targetSectionId2 = R.id.card_blow;
        final int targetSectionId3 = R.id.card_vital;
        final int targetSectionId4 = R.id.card_life_style;
        final int targetSectionId5 = R.id.card_nutrition;

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                View targetSection ;
                switch (j){
                    case 1 : targetSection = findViewById(targetSectionId1);break;
                    case 2 : targetSection = findViewById(targetSectionId2);break;
                    case 3 : targetSection = findViewById(targetSectionId3);break;
                    case 4 : targetSection = findViewById(targetSectionId4);break;
                    case 5 : targetSection = findViewById(targetSectionId5);break;
                    default: targetSection = findViewById(targetSectionId0);break;

                }
                if (targetSection != null) {
                    scrollView.smoothScrollTo(0, targetSection.getTop());
                }
            }
        });


        ImageButton buttonBack =findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void init(){
        setCurrentDateAndTime();
        performDiabetic();
        performBlow();
        performLifeStyle();
        performHealthScore();
        performVitalData();
        setProgressSmall();
        performWorkoutSlider();
//        vitalData();

//        performCalories();
//        caloriesChart();
//        performBMI();


        // Dialogs.hideLoadingDialog();
    }
    private void setCurrentDateAndTime(){
        TextView txtCurrentDate = findViewById(R.id.txt_current_date);
        TextView txtCurrentTime= findViewById(R.id.txt_current_time);

        txtCurrentDate.setText(date);
        txtCurrentTime.setText(time);

    }

    private void performDiabetic(){

        TextView textViewDiabeticScore = findViewById(R.id.txt_diabetic_score);
        TextView txtDiabeticProfileName = findViewById(R.id.txt_dibetic_profile_name);
        TextView txtDiabeticScoreProfileName = findViewById(R.id.txt_diabetic_score_profile_name);
        TextView txtDiabeticProfileSuggestion = findViewById(R.id.txt_diabetic_suggestion);
        TextView txtDiabeticStatus = findViewById(R.id.txt_progress_bar);
        TextView txtDiabeticMessage = findViewById(R.id.txt_diabetic_message);
        Button buttonDiabeticScroll=findViewById(R.id.button_diabetic_scroll);
        CircularProgressIndicator progressDiabeticMain=findViewById(R.id.progress_diabetic_score);

        ImageView imgDiabeticWarning =findViewById(R.id.img_diabetic_warning);

        String profileName = name;


        String profileName1 = profileName + ",<br />You're <b><font color=\"#252525\">" + "Diabetic Score" + "</font></b> is";
        Spanned newProfileName = Html.fromHtml(profileName1);
        txtDiabeticScoreProfileName.setText(newProfileName);

        DiabeticAnalysis.setDiabetic(
                getApplicationContext(),
                diabeticScore,
                profileName,
                progressDiabeticMain,
                textViewDiabeticScore,
                txtDiabeticStatus,
                txtDiabeticProfileName,
                txtDiabeticProfileSuggestion,
                txtDiabeticMessage,
                imgDiabeticWarning
        );

        buttonDiabeticScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, YLifeStyle);
            }
        });
        txtDiabeticMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceSheet.show();
            }
        });
    }

    private void performBlow(){
        TextView txtBlowScore =findViewById(R.id.txt_blow_score);
        TextView txtBlowScoreProfileName =findViewById(R.id.txt_blow_score_profile_name);
        TextView txtBlowScoreSuggestion =findViewById(R.id.txt_blow_profile_name_footer);
        TextView txtBlowScoreProfileNameFooter =findViewById(R.id.txt_blow_suggestion_footer);
        TextView txtBlowMessage =findViewById(R.id.txt_blow_message);
        CircularProgressIndicator blowScoreProgress = findViewById(R.id.progress_blow_score);
        Button buttonScroll = findViewById(R.id.button_blow_scroll);
        ImageView imgBlowWarning = findViewById(R.id.img_blow_warning);


        String profileName = name;


        String profileName1 = profileName + ",<br />Your <b><font color=\"#252525\">" + "Respiratory Score" + "</font></b> is";
        Spanned newProfileName = Html.fromHtml(profileName1);
        txtBlowScoreProfileName.setText(newProfileName);

        buttonScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, YLifeStyle);
            }
        });

        BlowScoreAnalysis.setProgress(getApplicationContext(),
                blowScore,
                profileName,
                blowScoreProgress,
                txtBlowScore,
                txtBlowScoreProfileNameFooter,
                txtBlowScoreSuggestion,
                txtBlowMessage,
                imgBlowWarning
        );

        txtBlowMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceSheet.show();
            }
        });


    }

    private void setProgressSmall(){
        CircularProgressIndicator progressSmDiabetic =findViewById(R.id.progress_diabetic_sm);
        CircularProgressIndicator progressSmVital =findViewById(R.id.progress_vitals_sm);
        CircularProgressIndicator progressSmBlow =findViewById(R.id.progress_blow_sm);
        CircularProgressIndicator progressSmLifestyle =findViewById(R.id.progress_lifestyle_sm);
        CircularProgressIndicator progressSmNutrition =findViewById(R.id.progress_nutrition_sm);

        TextView txtSmDiabetic = findViewById(R.id.txt_diabetic_sm);
        TextView txtSmVitals= findViewById(R.id.txt_vitals_sm);
        TextView txtSmBlow= findViewById(R.id.txt_blow_sm);
        TextView txtSmLifestyle= findViewById(R.id.txt_lifestyle_sm);
        TextView txtSmNutrition= findViewById(R.id.txt_nutrition_sm);


        double blow=2.8;


        setProgressSm(progressSmDiabetic,txtSmDiabetic,diabeticScore,100,1 );
        setProgressSm(progressSmVital,txtSmVitals,vitalScore,100,2 );
        setProgressSm(progressSmBlow,txtSmBlow,blowScore,100,3 );
        setProgressSm(progressSmLifestyle,txtSmLifestyle,OverallLifeStyleScore,100,4 );
        setProgressSm(progressSmNutrition,txtSmNutrition,detailed_nutrition_score,100,5 );

    }

    @SuppressLint("SetTextI18n")
    private void setProgressSm(CircularProgressIndicator circularProgressIndicator, TextView textView, double score, double max, int type){


        circularProgressIndicator.setCurrentProgress((int)score);
        circularProgressIndicator.setMaxProgress(max);


        double roundedValue = Math.round(score);

        if (score - Math.floor(score) >= 0.5) {
            roundedValue = Math.ceil(score);
        }
        int scoreNew = (int) roundedValue;

        if(scoreNew<70){
            circularProgressIndicator.setProgressColor(getResources().getColor(R.color.red));
        }else if(scoreNew < 80){
            circularProgressIndicator.setProgressColor(getResources().getColor(R.color.yellow));
        }else{
            circularProgressIndicator.setProgressColor(getResources().getColor(R.color.green));
        }
        textView.setText(String.valueOf((int)scoreNew)+"%");

    }


    private void performVitalData(){

        TextView txtVitalProfileName =findViewById(R.id.txt_vital_score_profile_name);
        TextView txtVitalScore =findViewById(R.id.txt_vital_score);
        CircularProgressIndicator circularProgressIndicator =findViewById(R.id.progress_vital_score);
        TextView txtVitalNameFooter =findViewById(R.id.txt_vital_profile_name_footer);
        TextView txtVitalSuggestion =findViewById(R.id.txt_vital_suggestion_footer);
        TextView txtVM =findViewById(R.id.text_vital_message);
        Button buttonVitalScroll =findViewById(R.id.button_vital_scroll);
        ImageView imgVitalWarning =findViewById(R.id.img_vital_warning);


        String profileName = name;
        String profileName1 = profileName + ",<br />Your <b><font color=\"#252525\">" + "Vital Score" + "</font></b> is";
        Spanned newProfileName = Html.fromHtml(profileName1);
        txtVitalProfileName.setText(newProfileName);

        buttonVitalScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, YLifeStyle);
            }
        });


        VitalAnalysis.setProgress(getApplicationContext(),
                vitalScore,
                profileName,
                circularProgressIndicator,
                txtVitalScore,
                txtVitalNameFooter,
                txtVitalSuggestion,
                txtVM,
                imgVitalWarning
        );

        txtVM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceSheet.show();
            }
        });



    }

    private void performLifeStyle(){

        TextView txtLifeStyleProfileName =findViewById(R.id.txt_life_style_score_profile_name);
        TextView txtLifeStyleScore =findViewById(R.id.txt_lifestyle_score);
        TextView txtLifeStyleMessage =findViewById(R.id.txt_lifestyle_message);

        TextView txtLifeStyleFooterName =findViewById(R.id.txt_life_style_score_footer_name);
        TextView txtLifeStyleFooterMessage=findViewById(R.id.txt_life_style_score_footer_message);
        ImageView imgLifeStyleWarning =findViewById(R.id.img_life_style_warning);



        CircularProgressIndicator circularProgressIndicatorLifeStyle =findViewById(R.id.progress_life_style_score);


        RadioButton star1,star2,star3,star4, star5;
        star1=findViewById(R.id.star1);
        star2=findViewById(R.id.star2);
        star3=findViewById(R.id.star3);
        star4=findViewById(R.id.star4);
        star5=findViewById(R.id.star5);


        //set profile name with message
        String profileName = name;
        String profileName1 = profileName + ",<br />Your <b><font color=\"#252525\">" + "Activity Score" + "</font></b> is";
        Spanned newProfileName = Html.fromHtml(profileName1);
        txtLifeStyleProfileName.setText(newProfileName);

        LifeStyleAnalysis.setLifeStyleScore(getApplicationContext(),
                OverallLifeStyleScore,
                circularProgressIndicatorLifeStyle,
                txtLifeStyleScore,
                txtLifeStyleMessage,
                txtLifeStyleFooterName,
                txtLifeStyleFooterMessage,
                profileName,
                imgLifeStyleWarning
        );


        txtLifeStyleMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceSheet.show();
            }
        });

        performNutrition();
    }
    private void performNutrition(){


        double nutritionScore =  detailed_nutrition_score;
        TextView txtNutritionProfileName =findViewById(R.id.txt_nutrition_profile_name);
        TextView txtNutritionScore =findViewById(R.id.txt_nutrition_score);
        TextView txtNutritionMessage =findViewById(R.id.txt_nutrition_message);
        TextView txtNutritionFooterName =findViewById(R.id.txt_nutritian_footer_name);
        TextView txtNutritionSuggestion =findViewById(R.id.txt_nutritian_footer_message);
        CircularProgressIndicator progressNutritionScore=findViewById(R.id.progress_nutrition);
        Button buttonViewSuggestion =findViewById(R.id.button_nutrition_view_suggestion);
        ImageView imgBadge =findViewById(R.id.img_nutrition_badge);
        Button buttonExpand =findViewById(R.id.button_expand);

        //set profile name with message
        String profileName =name;
        String profileName1 = profileName+",<br />Your <b>lifestyle score </b>is";
        Spanned newProfileName = Html.fromHtml(profileName1);
        txtNutritionProfileName.setText(newProfileName);


        LifeStyleAnalysis.setNutrition(getApplicationContext(),
                nutritionScore,
                txtNutritionScore,
                progressNutritionScore,
                profileName,
                txtNutritionMessage,
                txtNutritionFooterName,
                txtNutritionSuggestion,
                imgBadge);



        buttonViewSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Suggestion.class);
                startActivity(intent);
            }
        });

        txtNutritionMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceSheet.show();
            }
        });

        buttonExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Expand.class);
                startActivity(intent);
            }
        });

    }

    private void performHealthScore(){
        TextView textViewHealthScoreProfileName =findViewById(R.id.txt_result_page_health_score_profile_name);
        TextView textViewHealthScoreValue =findViewById(R.id.txt_result_page_health_score_value);
        TextView textViewHealthScoreTitle =findViewById(R.id.txt_result_page_health_score_title);
        TextView textViewHealthScoreSuggestion =findViewById(R.id.txt_result_page_health_score_suggestion);
        CircularProgressIndicator progressHealthScore =findViewById(R.id.result_page_progress_health_score);

        ImageButton imageButton =findViewById(R.id.button_reference);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceSheet.show();
            }
        });

        //set profile name with message
        String profileName =name;
        String profileName1 = profileName+ ", This is your";
        Spanned newProfileName = Html.fromHtml(profileName1);
        textViewHealthScoreProfileName.setText(newProfileName);

        HealthScoreAnalysis.setProgress(
                getApplicationContext(),
                progressHealthScore,
                healthScore,
                profileName,
                textViewHealthScoreValue,
                textViewHealthScoreTitle,
                textViewHealthScoreSuggestion
        );


    }


    private void performWorkoutSlider(){

        // Get the RecyclerView from the layout
        recyclerView = findViewById(R.id.recycler_view);

        // Create a list of data to populate the RecyclerView
        List<WorkoutSliderDataModel> dataList = new ArrayList<>();
        dataList.add(new WorkoutSliderDataModel("Modified Push-ups", "18 mins", R.drawable.pushup));
        dataList.add(new WorkoutSliderDataModel("Simple Cardio", "10 mins", R.drawable.cardio));


        // Create an instance of the custom adapter
        adapter = new WorkoutSliderAdapter(dataList,1);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);

        // Set the layout manager to display items linearly
        // Set the layout manager to display items horizontally
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);

        CircleIndicator2 indicator = findViewById(R.id.indicator);
        indicator.attachToRecyclerView(recyclerView, pagerSnapHelper);

        // optional
        adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());


        Button buttonSuggestion =findViewById(R.id.button_main_go_to_suggestion);
        buttonSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Suggestion.class);
                startActivity(intent);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getCurrentDayName() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault());
    }

}
