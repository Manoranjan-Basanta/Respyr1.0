package com.humorstech.respyr.reading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.reading.food_breakup.FoodBreakUP;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class Expand extends AppCompatActivity {


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

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);
        StatusBarColor statusBarColor= new StatusBarColor(Expand.this);
        statusBarColor.setDarkColor(getResources().getColor(R.color.white));

        SharedPreferences sharedPreferences = getSharedPreferences("result_data", Context.MODE_PRIVATE);
        String curr_calStr = sharedPreferences.getString("curr_cal", "715");
        String curr_carStr = sharedPreferences.getString("curr_car", "715");
        String curr_proStr = sharedPreferences.getString("curr_pro", "715");
        String curr_fatStr = sharedPreferences.getString("curr_fat", "715");
        String curr_fibStr = sharedPreferences.getString("curr_fib", "715");
        String reco_calStr = sharedPreferences.getString("reco_cal", "715");
        String reco_carStr = sharedPreferences.getString("reco_car", "715");
        String reco_proStr = sharedPreferences.getString("reco_pro", "715");
        String reco_fatStr = sharedPreferences.getString("reco_fat", "715");
        String reco_fibStr = sharedPreferences.getString("reco_fib", "715");
        name = sharedPreferences.getString("name", "");

        curr_cal = Double.parseDouble(curr_calStr);
        curr_car = Double.parseDouble(curr_carStr);
        curr_pro = Double.parseDouble(curr_proStr);
        curr_fat = Double.parseDouble(curr_fatStr);
        curr_fib = Double.parseDouble(curr_fibStr);
        reco_cal = Double.parseDouble(reco_calStr);
        reco_car = Double.parseDouble(reco_carStr);
        reco_pro = Double.parseDouble(reco_proStr);
        reco_fat = Double.parseDouble(reco_fatStr);
        reco_fib = Double.parseDouble(reco_fibStr);


        performCalories();


        ImageButton buttonBack =findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void performCalories(){

        /// calories
        TextView txtCaloriesScore = findViewById(R.id.txt_calories_score);
        TextView txtActualCalories = findViewById(R.id.txt_calories_actual);
        TextView txtRecommendedCalories = findViewById(R.id.txt_calories_recomoneded);
        TextView txtCaloriesDifference = findViewById(R.id.txt_calories_difference);



        double actualCalories = curr_cal;
        double recommendedCalories  =reco_cal;
        double caloriesScore = (actualCalories / recommendedCalories) * 100;

        CaloriesAnalysis.performTexts(getApplicationContext(),
                caloriesScore,
                name,
                actualCalories,
                recommendedCalories,
                txtCaloriesScore,
                txtActualCalories,
                txtRecommendedCalories,
                txtCaloriesDifference,
                1
        );

        // carbo hydrates
        TextView txtCarboHydratesScore = findViewById(R.id.txt_carbo_hydrate_score);
        TextView txtActualCarboHydrates = findViewById(R.id.txt_carbo_hydrate_actual);
        TextView txtRecommendedCarboHydrates = findViewById(R.id.txt_carbo_hydrate_recommended);
        TextView txtCarboHydratesDifference = findViewById(R.id.txt_carbo_hydrate_difference);



        double actualCarboHydrates = curr_car;
        double recommendedCarboHydrates  = reco_car;
        double caloriesCarboHydrates = (actualCarboHydrates / recommendedCarboHydrates) * 100;

        CaloriesAnalysis.performTexts(getApplicationContext(),
                caloriesCarboHydrates,
                name,
                actualCarboHydrates,
                recommendedCarboHydrates,
                txtCarboHydratesScore,
                txtActualCarboHydrates,
                txtRecommendedCarboHydrates,
                txtCarboHydratesDifference,
                2
        );

        // protein
        TextView txtProteinScore = findViewById(R.id.txt_protein_score);
        TextView txtActualProtein = findViewById(R.id.txt_actual_protein);
        TextView txtRecommendedProtein = findViewById(R.id.txt_recommended_protein);
        TextView txtDifferenceProtein = findViewById(R.id.txt_difference_protein);


        double actualProtein = curr_pro;
        double recommendedProtein  = reco_pro;
        double scoreProtein = (actualProtein / recommendedProtein) * 100;

        CaloriesAnalysis.performTexts(getApplicationContext(),
                scoreProtein,
                name,
                actualProtein,
                recommendedProtein,
                txtProteinScore,
                txtActualProtein,
                txtRecommendedProtein,
                txtDifferenceProtein,
                3
        );

        // fats
        TextView txtFatsScore = findViewById(R.id.txt_fats_score);
        TextView txtActualFats = findViewById(R.id.txt_fats_actual);
        TextView txtRecommendedFats= findViewById(R.id.txt_fats_recommended);
        TextView txtDifferenceFats = findViewById(R.id.txt_fats_difference);


        double actualFats=  curr_fat;
        double recommendedFats  = reco_fat;
        double scoreFats = (actualFats / recommendedFats) * 100;

        CaloriesAnalysis.performTexts(getApplicationContext(),
                scoreFats,
                name,
                actualFats,
                recommendedFats,
                txtFatsScore,
                txtActualFats,
                txtRecommendedFats,
                txtDifferenceFats,
                4
        );

        // fibre
        TextView txtFibreScore = findViewById(R.id.txt_fibre_score);
        TextView txtActualFibre = findViewById(R.id.txt_fibre_actual);
        TextView txtRecommendedFibre= findViewById(R.id.txt_fibre_recommended);
        TextView txtDifferenceFibre= findViewById(R.id.txt_fibre_difference);


        double actualFibre=  curr_fib;
        double recommendedFibre = reco_fib ;
        double scoreFibre = (actualFibre / recommendedFibre) * 100;

        CaloriesAnalysis.performTexts(getApplicationContext(),
                scoreFibre,
                name,
                actualFibre,
                recommendedFibre,
                txtFibreScore,
                txtActualFibre,
                txtRecommendedFibre,
                txtDifferenceFibre,
                5
        );



        /// perform progress
        //Buttons
        TextView btnCalories, btnCarbohydrate, btnProtein, btnFats, btnFiber;
        btnCalories=findViewById(R.id.button_calories);
        btnCarbohydrate=findViewById(R.id.button_carbohydrate);
        btnProtein=findViewById(R.id.button_proteins);
        btnFats=findViewById(R.id.button_fats);
        btnFiber=findViewById(R.id.button_fibre);

        // Layouts
        RelativeLayout layoutCalories =findViewById(R.id.layout_calories_card);
        RelativeLayout layoutCarboHydrate =findViewById(R.id.layout_carbohydrate_card);
        RelativeLayout layoutProtein=findViewById(R.id.layout_protein_card);
        RelativeLayout layoutFats=findViewById(R.id.layout_fats_card);
        RelativeLayout layoutFiber=findViewById(R.id.layout_fiber_card);


        // Circular Progress
        CircularProgressIndicator p1=findViewById(R.id.p1);
        CircularProgressIndicator p2=findViewById(R.id.p2);
        CircularProgressIndicator p3=findViewById(R.id.p3);
        CircularProgressIndicator p4=findViewById(R.id.p4);
        CircularProgressIndicator p5=findViewById(R.id.p5);
        CircularProgressIndicator p6=findViewById(R.id.p6);
        CircularProgressIndicator p7=findViewById(R.id.p7);
        CircularProgressIndicator p8=findViewById(R.id.p8);
        CircularProgressIndicator p9=findViewById(R.id.p9);
        CircularProgressIndicator p10=findViewById(R.id.p10);
        CircularProgressIndicator p11=findViewById(R.id.p11);
        CircularProgressIndicator p12=findViewById(R.id.p12);
        CircularProgressIndicator p13=findViewById(R.id.p13);
        CircularProgressIndicator p14=findViewById(R.id.p14);
        CircularProgressIndicator p15=findViewById(R.id.p15);
        CircularProgressIndicator p16=findViewById(R.id.p16);
        CircularProgressIndicator p17=findViewById(R.id.p17);
        CircularProgressIndicator p18=findViewById(R.id.p18);
        CircularProgressIndicator p19=findViewById(R.id.p19);
        CircularProgressIndicator p20=findViewById(R.id.p20);
        CircularProgressIndicator p21=findViewById(R.id.p21);
        CircularProgressIndicator p22=findViewById(R.id.p22);
        CircularProgressIndicator p23=findViewById(R.id.p23);
        CircularProgressIndicator p24=findViewById(R.id.p24);
        CircularProgressIndicator p25=findViewById(R.id.p25);


        // default
        CaloriesAnalysis.performButtons(getApplicationContext(),1,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
        CaloriesAnalysis.performLayouts(getApplicationContext(),1,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);


        CaloriesAnalysis.setProgress(getApplicationContext(),1,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p1,p2,p3,p4,p5);

        btnCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CaloriesAnalysis.performButtons(getApplicationContext(),1,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
                CaloriesAnalysis.performLayouts(getApplicationContext(),1,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
                CaloriesAnalysis.setProgress(getApplicationContext(),1,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p1,p2,p3,p4,p5);

            }
        });
        btnCarbohydrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaloriesAnalysis.performButtons(getApplicationContext(),2,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
                CaloriesAnalysis.performLayouts(getApplicationContext(),2,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
                CaloriesAnalysis.setProgress(getApplicationContext(),2,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p6,p7,p8,p9,p10);

            }
        });
        btnProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaloriesAnalysis.performButtons(getApplicationContext(),3,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
                CaloriesAnalysis.performLayouts(getApplicationContext(),3,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
                CaloriesAnalysis.setProgress(getApplicationContext(),3,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p11,p12,p13,p14,p15);
            }
        });
        btnFats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaloriesAnalysis.performButtons(getApplicationContext(),4,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
                CaloriesAnalysis.performLayouts(getApplicationContext(),4,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
                CaloriesAnalysis.setProgress(getApplicationContext(),4,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p16,p17,p18,p19,p20);
            }
        });
        btnFiber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaloriesAnalysis.performButtons(getApplicationContext(),5,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
                CaloriesAnalysis.performLayouts(getApplicationContext(),5,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
                CaloriesAnalysis.setProgress(getApplicationContext(),5,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p21,p22,p23,p24,p25);
            }
        });

        Button buttonViewFoodBreakup=findViewById(R.id.button_view_food_breakup);
        buttonViewFoodBreakup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FoodBreakUP.class);
                startActivity(intent);
            }
        });


    }

}