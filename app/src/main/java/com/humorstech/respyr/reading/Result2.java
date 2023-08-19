package com.humorstech.respyr.reading;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.humorstech.respyr.Dashboard;
import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.utills.BloodPressureResults;
import com.humorstech.respyr.utills.ReadingParameters;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class Result2 extends AppCompatActivity {

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


    double blowScore;
    String  date, time;


    TextView txtLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        StatusBarColor statusBarColor= new StatusBarColor(Result2.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));


        txtLog = findViewById(R.id.txt_log);
        txtLog.setMovementMethod(new ScrollingMovementMethod());
        init();


        Button button =findViewById(R.id.button_view_analysis);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 0);
                startActivity(intent);
            }
        });

        LinearLayout card1 =findViewById(R.id.card_lungs);
        LinearLayout card2 =findViewById(R.id.card_vital);
        LinearLayout card3 =findViewById(R.id.card_diabetic);
        LinearLayout card4 =findViewById(R.id.card_nutrition);
        LinearLayout card5 =findViewById(R.id.card_activity);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 2);
                startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 3);
                startActivity(intent);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 1);
                startActivity(intent);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 5);
                startActivity(intent);
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                intent.putExtra("scroll", 4);
                startActivity(intent);
            }
        });


        ImageButton buttonBack =findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        setCurrentDateAndTime();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void init(){
        getIntentData();
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if (intent!=null){
            response = intent.getStringExtra("response");
        }

        SharedPreferences sharedPreferences = getSharedPreferences(ReadingParameters.TITLE, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(ReadingParameters.NAME, "Sagar");
        waterIntake = sharedPreferences.getString(ReadingParameters.WATER_INTAKE, "7");
        smokingUnits = sharedPreferences.getString(ReadingParameters.SMOKING_UNITS, "2");
        isTakenAlcohol = sharedPreferences.getString(ReadingParameters.IS_TAKEN_ALCOHOL, "80");
        sleepHours = sharedPreferences.getString(ReadingParameters.SLEEP_HOURS, "8");
        exerciseInMinutes = sharedPreferences.getString(ReadingParameters.EXERCISE_IN_MINUTES, "120");
        foodName = sharedPreferences.getString(ReadingParameters.FOOD_NAME, "food_name_0=Dal Mixed&food_name_1=Rice&");
        foodQuantity = sharedPreferences.getString(ReadingParameters.FOOD_QUANTITY, "food_quantity_0=500&food_quantity_1=500&");
        foodCount = sharedPreferences.getString(ReadingParameters.FOOD_COUNT, "2");
        isHadBreakfast = sharedPreferences.getString(ReadingParameters.IS_HAD_BREAKFAST, "true");
        isHadLunch = sharedPreferences.getString(ReadingParameters.IS_HAD_LUNCH, "false");
        isHadDinner = sharedPreferences.getString(ReadingParameters.IS_HAD_DINNER,"false");
        gender = sharedPreferences.getString(ReadingParameters.GENDER, "male");
        age = sharedPreferences.getString(ReadingParameters.AGE, "26");
        height = sharedPreferences.getString(ReadingParameters.HEIGHT, "180");
        weight = sharedPreferences.getString(ReadingParameters.WEIGHT, "74");

        foodCount="1";

        SharedPreferences sharedPreferences2 = getSharedPreferences(BloodPressureResults.TITLE, Context.MODE_PRIVATE);
        SP = sharedPreferences2.getString(BloodPressureResults.SYSTOLIC, "120");
        DP = sharedPreferences2.getString(BloodPressureResults.DIASTOLIC, "74");
        Beats = sharedPreferences2.getString(BloodPressureResults.FINAL_BPM, "98");
        Breath = sharedPreferences2.getString(BloodPressureResults.BREATH_RATE, "12");
        spo2 = sharedPreferences2.getString(BloodPressureResults.SPO2, "98");
        bpm1 = sharedPreferences2.getString(BloodPressureResults.BPM1, "65");
        bpm2 = sharedPreferences2.getString(BloodPressureResults.BPM2, "74");


        txtLog.append("SP: " + SP + "\n");
        txtLog.append("Name: " + name + "\n");
        txtLog.append("DP: " + DP + "\n");
        txtLog.append("Beats: " + Beats + "\n");
        txtLog.append("Breath: " + Breath + "\n");
        txtLog.append("Spo2: " + spo2 + "\n");
        txtLog.append("Bpm1: " + bpm1 + "\n");
        txtLog.append("Bpm2: " + bpm2 + "\n");
        txtLog.append("Water Intake: " + waterIntake + "\n");
        txtLog.append("Smoking Units: " + smokingUnits + "\n");
        txtLog.append("Is Taken Alcohol: " + isTakenAlcohol + "\n");
        txtLog.append("Sleep Hours: " + sleepHours + "\n");
        txtLog.append("Exercise Minutes: " + exerciseInMinutes + "\n");
        txtLog.append("Food Name: " + foodName + "\n");
        txtLog.append("Food Quantity: " + foodQuantity + "\n");
        txtLog.append("Food Count: " + foodCount + "\n");
        txtLog.append("Had Breakfast: " + isHadBreakfast + "\n");
        txtLog.append("Had Lunch: " + isHadLunch + "\n");
        txtLog.append("Had Dinner: " + isHadDinner + "\n");
        txtLog.append("USER_ID: " + USER_ID + "\n");
        txtLog.append("Gender: " + gender + "\n");
        txtLog.append("Age: " + age + "\n");
        txtLog.append("Height: " + height + "\n");
        txtLog.append("Weight: " + weight + "\n");


       //   response="{\"data\":[{\"status\":1,\"ace\":\"0.17162892\",\"pp_press\":\"73\",\"etholpp\":\"10\",\"battry\":null,\"finaltemp\":\"31\",\"duration\":\"90\",\"rawmic\":null,\"bmhumid\":null,\"besthumid\":0,\"bpm\":null,\"valpress\":null,\"peak_press\":null,\"nopp\":null,\"cap\":\"0\",\"val1820\":\"180\",\"valFinal1820\":\"181\",\"MVacetone\":3.912}]}";
          decodeJson(response);
//        txtLog.append("Response 1 -->"+response + "\n");

    }


    private void decodeJson(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            JSONObject dataObject = dataArray.getJSONObject(0);
            double acetone = dataObject.getDouble("ace");
            double ethanol = dataObject.getDouble("etholpp");
            double MV_acetone = dataObject.getDouble("MVacetone");
            double blow = dataObject.getDouble("cap");

         // fetchDiabeticScore("120", "74", "98","24", "98", String.valueOf(MV_acetone), String.valueOf(ethanol),String.valueOf(blow));
           fetchDiabeticScore(SP, DP, Beats,age, "98", String.valueOf(MV_acetone), String.valueOf(ethanol),String.valueOf(blow));


        }catch (Exception e){

        }
    }


    private void fetchDiabeticScore(String SP, String DP,String heartRate,String age,String spo2,String acetone, String ethanol, String blow ){


        RequestParams params = new RequestParams();
        params.put("acetone",acetone);
        params.put("ethnol",ethanol);
        params.put("spo2",spo2);
        params.put("heart_rate",heartRate);
        params.put("diastolic",DP);
        params.put("systolic",SP);
        params.put("blow",blow);
        params.put("age",age);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get( "https://humorstech.com/humors/json_curl/db_vital.php",params,

                new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {

                        Dialogs.showLoadingDialog(Result2.this,"Fetching result.." );

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {



                        String response2 = new String(responseBody);
                        txtLog.append("\n");
                        txtLog.append("Response 2--->"+response2 + "\n");

                        try {


                            JSONObject jsonObject = new JSONObject(response2);

                            double bloodPressureScore = jsonObject.getDouble("Blood_Pressure_Score");
                            String diabeticPrediction = jsonObject.getString("Dibetic_Prediction");
                            double diabeticScore = jsonObject.getDouble("Dibetic_Score");
                            double diabeticValue = jsonObject.getDouble("Dibetic_value");
                            double heartRateScore = jsonObject.getDouble("Heart_Rate_Score");
                            double overallVitalScore = jsonObject.getDouble("Overall_Vital_Score");
                            double spo2Score = jsonObject.getDouble("SPO2_Score");
                            blowScore = jsonObject.getDouble("Blow_Score");

                            System.out.println("diabeticScore-------->"+diabeticScore);
                            System.out.println("overallVitalScore-------->"+overallVitalScore);
                            System.out.println("Blow score-------->"+blowScore);


                            String activity_level = "sedentary";
                            String type = "veg";


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                fetAllResult(
                                        activity_level,String.valueOf(diabeticScore), isHadBreakfast,
                                        isHadLunch, isHadDinner, String.valueOf(overallVitalScore) ,
                                        height, age, weight,gender,
                                        foodName,exerciseInMinutes, waterIntake , isTakenAlcohol,
                                        smokingUnits,sleepHours, getCurrentDayName(),  type);
                            }


                        }catch (JSONException e){
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


                    }

                });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void fetAllResult(
            String activity_level,String DB_Score, String breakfast,
            String lunch, String dinner, String VT_Score ,
            String height, String age, String weight, String gender,
            String foodItems,String exercise_hours, String water_consumption , String alcohol_consumption,
            String smoking_units,String sleepHours, String day, String veg

    ){

        String breakfastNew ;
        String lunchNew ;
        String dinnerNew ;
        if (breakfast.equals("true")){
            breakfastNew="yes";
        }else{
            breakfastNew="no";
        }

        if (lunch.equals("true")){
            lunchNew="yes";
        }else{
            lunchNew="no";
        }

        if (dinner.equals("true")){
            dinnerNew="yes";
        }else{
            dinnerNew="no";
        }




        // Create RequestParams object
        RequestParams params = new RequestParams();
        // Add parameters to the RequestParams object
        params.put("activity_level", "sedentary");
        params.put("DB_Score", DB_Score);
        params.put("breakfast", breakfastNew);
        params.put("lunch", lunchNew);
        params.put("dinner", dinnerNew);
        params.put("VT_Score", VT_Score);
        params.put("height", height);
        params.put("age", age);
        params.put("weight", weight);
        params.put("gender", gender);
        params.put("food_intake", foodCount);
        params.put("food_name", foodName);
        params.put("food_quantity", foodQuantity);
        params.put("exercise_hours", exercise_hours);
        params.put("sleep_hours", sleepHours);
        params.put("water_consumption", waterIntake);
        params.put("alcohol_consumption", "0");
        params.put("smoking_units", smoking_units);
        params.put("day", getCurrentDayName());
        params.put("type", "veg");


       // String dummyURL = "https://humorstech.com/humors/json_curl/life_style.php?activity_level=sedentary&DB_Score=80&breakfast=no&lunch=yes&dinner=yes&VT_Score=90&height=187&age=33&weight=45&gender=male&food_intake=2&food_name=food_name_0=Rice,food_name_1=dal&food_quantity=food_quantity_0=1000,food_quantity_1=1000&exercise_hours=20&sleep_hours=8&water_consumption=1500&alcohol_consumption=0&smoking_units=0&day=Tuesday&type=veg";
        String dummyURL = "https://humorstech.com/humors/json_curl/life_style.php?";


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(dummyURL,params, new AsyncHttpResponseHandler() {
            //   client.get(dummyURL, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                txtLog.append("Response 3--->"+params + "\n");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String response3=new String(response);
                    txtLog.append("\n");
                    txtLog.append("Response 3--->"+response3 + "\n");
                    decodeResultData(response3);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    private void decodeResultData(String jsonData){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        JsonArray dataArray = jsonObject.getAsJsonArray("data");

        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject item = dataArray.get(i).getAsJsonObject();

            healthScore = item.get("healthScore").getAsDouble();
            String healthScoreString = item.get("healthScore").getAsString();

            if (healthScoreString!=null){
                diabeticScore = item.get("diabeticScore").getAsDouble();
                vitalScore = item.get("vitalScore").getAsDouble();
                bmi = item.get("bmi").getAsDouble();
                bmr = item.get("bmr").getAsDouble();
                curr_cal = item.get("curr_cal").getAsDouble();
                curr_car = item.get("curr_car").getAsDouble();
                curr_pro = item.get("curr_pro").getAsDouble();
                curr_fat = item.get("curr_fat").getAsDouble();
                curr_fib = item.get("curr_fib").getAsDouble();
                reco_cal = item.get("reco_cal").getAsDouble();
                reco_car = item.get("reco_car").getAsDouble();
                reco_pro = item.get("reco_pro").getAsDouble();
                reco_fat = item.get("reco_fat").getAsDouble();
                reco_fib = item.get("reco_fib").getAsDouble();
                OverallLifeStyleScore = item.get("OverallLifeStyleScore").getAsDouble();
                detailedLifeStyleScore_lfstyle_score = item.get("detailedLifeStyleScore_lfstyle_score").getAsDouble();
                detailed_nutrition_score= item.get("OverallNutrientScore").getAsDouble();


                String breakfast_sugs= item.get("breakfast_sugs").getAsString();
                String lunch_sugs= item.get("lunch_sug").getAsString();
                String dinner_sugs= item.get("dinner_sug").getAsString();

                JsonArray LifeStyleSuggestion = item.get("LifeStyleSuggestions").getAsJsonArray();
                BMISuggestions = item.get("BMISuggestions").getAsString();


                SharedPreferences sharedPreferences = getSharedPreferences("result_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("health_score", String.valueOf(healthScore));
                editor.putString("diabetic_score", String.valueOf(diabeticScore));
                editor.putString("vital_score", String.valueOf(vitalScore));
                editor.putString("bmi", String.valueOf(bmi));
                editor.putString("bmr", String.valueOf(bmr));
                editor.putString("curr_cal", String.valueOf(curr_cal));
                editor.putString("curr_car", String.valueOf(curr_car));
                editor.putString("curr_pro", String.valueOf(curr_pro));
                editor.putString("curr_fat", String.valueOf(curr_fat));
                editor.putString("curr_fib", String.valueOf(curr_fib));
                editor.putString("reco_cal", String.valueOf(reco_cal));
                editor.putString("reco_car", String.valueOf(reco_car));
                editor.putString("reco_pro", String.valueOf(reco_pro));
                editor.putString("reco_fat", String.valueOf(reco_fat));
                editor.putString("reco_fib", String.valueOf(reco_fib));
                editor.putString("overall_lifestyle_score", String.valueOf(OverallLifeStyleScore));
                editor.putString("OverallNutrientScore", String.valueOf(detailed_nutrition_score));
                editor.putString("name", String.valueOf(name));
                editor.putString("breakfast_sugs", String.valueOf(breakfast_sugs));
                editor.putString("lunch_sugs", String.valueOf(lunch_sugs));
                editor.putString("dinner_sugs", String.valueOf(dinner_sugs));
                editor.putString("LifeStyleSuggestion", String.valueOf(LifeStyleSuggestion));
                editor.putString("BMISuggestions", String.valueOf(BMISuggestions));
                editor.putString("response", String.valueOf(response));
                editor.putString("blowScore", String.valueOf(blowScore));
                editor.putString("curr_date", String.valueOf(date));
                editor.putString("curr_time", String.valueOf(time));
                editor.apply();
            }

        }

        txtLog.append("\n");
        txtLog.append("Clinical Scores--------------------------------------"+"\n");
        txtLog.append("Overall Health Score--->"+healthScore + "\n");
        txtLog.append("Diabetic Score--->"+diabeticScore + "\n");
        txtLog.append("Blow Score--->"+blowScore + "\n");
        txtLog.append("Vital Score--->"+vitalScore + "\n");
        txtLog.append("LifeStyle Scores---------------------------------------"+"\n");
        txtLog.append("LifeStyle Score--->"+OverallLifeStyleScore + "\n");
        txtLog.append("Nutrition Health Score--->"+detailed_nutrition_score + "\n");
        txtLog.append("Date--->"+date + "\n");
        txtLog.append("Time--->"+time + "\n");

        // display----------------
        perform3Scores(blowScore, vitalScore, diabeticScore);
        performLifeStyle(OverallLifeStyleScore, detailed_nutrition_score);
        performOverallHealthScore(healthScore);
        Dialogs.hideLoadingDialog();


        // clear shared preference data
        SharedPreferences sharedPreferences = getSharedPreferences(ReadingParameters.TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        SharedPreferences sharedPreferences2 = getSharedPreferences(BloodPressureResults.TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor2.clear();
        editor2.apply();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getCurrentDayName() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault());
    }

    private void setCurrentDateAndTime(){
        TextView txtCurrentDate = findViewById(R.id.txt_current_date);
        TextView txtCurrentTime= findViewById(R.id.txt_current_time);

        // Get the current date and time
        LocalDateTime currentDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime = LocalDateTime.now();

            // Define the desired date and time format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d MMM yyyy");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("H:mm a");

            // Format the current date and time
            date = currentDateTime.format(formatter);
            time = currentDateTime.format(formatter2);
            txtCurrentDate.setText(date);
            txtCurrentTime.setText(time);

        }

    }

    private void perform3Scores(double lungsScore, double vitalScore, double diabeticScore){

        TextView txtLungsScore =findViewById(R.id.txt_lungs_score);
        TextView txtLungsStatus =findViewById(R.id.txt_lungs_status);
        TextView txtLungsMessage=findViewById(R.id.txt_lungs_message);
        RoundedProgressBar  progressLungs =findViewById(R.id.progress_lungs);

        TextView txtVitalScore =findViewById(R.id.txt_vital_score);
        TextView txtVitalStatus=findViewById(R.id.txt_vitals_status);
        TextView txtVitalMessage =findViewById(R.id.txt_vitals_message);
        RoundedProgressBar  progressVital=findViewById(R.id.progress_vitals);

        TextView txtDiabeticScore =findViewById(R.id.txt_diabetic_score);
        TextView txtDiabeticStatus =findViewById(R.id.txt_diabetic_status);
        TextView txtDiabeticMessage=findViewById(R.id.txt_diabetic_message);
        RoundedProgressBar  progressDiabetic=findViewById(R.id.progress_diabetic);

        setProgress(lungsScore, txtLungsScore, progressLungs, txtLungsStatus,txtLungsMessage);
        setProgress(vitalScore, txtVitalScore, progressVital, txtVitalStatus,txtVitalMessage);
        setProgress(diabeticScore, txtDiabeticScore, progressDiabetic, txtDiabeticStatus,txtDiabeticMessage);
    }

    private void setProgress(double score, TextView textScore, RoundedProgressBar roundedProgressBar, TextView status,TextView message ){


        final int unHealthy = 70;
        final int moderate = 80;
        final int healthy = 100;
        final String[] titles = {"Good!","Fair!","Poor!"};
        final String[] subTitles = {"Everything looks good!","Monitor daily for a week!","Come back in a week!"};



        // set progress to text and progress bar
        double roundedValue = Math.round(score);

        if (score - Math.floor(score) >= 0.5) {
            roundedValue = Math.ceil(score);
        }

        textScore.setText(String.valueOf((int)roundedValue)+"%");
        roundedProgressBar.setProgressPercentage(roundedValue, true);



        if(score<=unHealthy){
            status.setText(titles[2]);
            message.setText(subTitles[2]);
            status.setTextColor(getResources().getColor(R.color.red));
            textScore.setTextColor(getResources().getColor(R.color.red));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.red));
        }else if (score<moderate){
            status.setText(titles[1]);
            message.setText(subTitles[1]);
            status.setTextColor(getResources().getColor(R.color.yellow));
            textScore.setTextColor(getResources().getColor(R.color.yellow));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.yellow));
        }else if(score<=healthy){
            status.setText(titles[0]);
            message.setText(subTitles[0]);
            status.setTextColor(getResources().getColor(R.color.green));
            textScore.setTextColor(getResources().getColor(R.color.green));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.green));
        }


    }

    private void performLifeStyle(double lifeStyleScore, double nutritionScore){

        TextView txtLifeStyleScore =findViewById(R.id.txt_activity_score);
        TextView txtLifeStyleStatus =findViewById(R.id.txt_activity_status);
        TextView txtLifeStyleMessage=findViewById(R.id.txt_activity_message);
        RoundedProgressBar  progressLifeStyle =findViewById(R.id.progress_activity);

        TextView txtNutritionScore =findViewById(R.id.txt_nutrition_score);
        TextView txtNutritionStatus =findViewById(R.id.txt_nutrition_status);
        TextView txtNutritionMessage=findViewById(R.id.txt_nutrition_message);
        RoundedProgressBar  progressNutrition=findViewById(R.id.progress_nutrition);

        setLifeStyleScores(1 ,lifeStyleScore,txtLifeStyleScore,progressLifeStyle,txtLifeStyleStatus,txtLifeStyleMessage );
        setLifeStyleScores(2 ,nutritionScore,txtNutritionScore,progressNutrition,txtNutritionStatus,txtNutritionMessage );
    }

    private void setLifeStyleScores(int i ,double score,TextView txtScore,RoundedProgressBar roundedProgressBar, TextView txtStatus, TextView txtMessage ){

        final int unHealthy = 70;
        final int moderate = 80;
        final int healthy = 100;
        final String[] titles = {"Good!","Fair!","Poor!"};
        final String[] subTitles = {"You are on fire","Keep pushing","Start moving"};
        final String[] subTitles1 = {"Focus on food","Eat better","Fueling up right"};



        // set progress to text and progress bar
        double roundedValue = Math.round(score);

        if (score - Math.floor(score) >= 0.5) {
            roundedValue = Math.ceil(score);
        }
        txtScore.setText(String.valueOf((int)roundedValue)+"%");
        roundedProgressBar.setProgressPercentage(roundedValue, true);


        if(score<=unHealthy){
            txtStatus.setText(titles[2]);

            if (i==1){
                txtMessage.setText(subTitles[2]);
            }else{
                txtMessage.setText(subTitles1[2]);
            }

            txtStatus.setTextColor(getResources().getColor(R.color.red));
            txtScore.setTextColor(getResources().getColor(R.color.red));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.red));
        }else if (score<moderate){
            txtStatus.setText(titles[1]);

            if (i==1){
                txtMessage.setText(subTitles[1]);
            }else{
                txtMessage.setText(subTitles1[1]);
            }


            txtStatus.setTextColor(getResources().getColor(R.color.yellow));
            txtScore.setTextColor(getResources().getColor(R.color.yellow));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.yellow));
        }else if(score<=healthy){
            txtStatus.setText(titles[0]);


            if (i==1){
                txtMessage.setText(subTitles[0]);
            }else{
                txtMessage.setText(subTitles1[0]);
            }


            txtStatus.setTextColor(getResources().getColor(R.color.green));
            txtScore.setTextColor(getResources().getColor(R.color.green));

            roundedProgressBar.setProgressDrawableColor(getResources().getColor(R.color.green));
        }


    }

    @SuppressLint("SetTextI18n")
    private void performOverallHealthScore(double overallHealthScore){

        TextView txtProfileName =findViewById(R.id.txt_profile_name);
        TextView txtOverallHealthScore =findViewById(R.id.txt_overall_health_score);
        RoundedProgressBar progressOverallHealthScore =findViewById(R.id.progress_overall_health_score);


        /// set profile name

        //Shubham,
        //Your Overall Health Score is
        String profileName = name;
        txtProfileName.setText(profileName + ",\nYour Overall Health Score is");

        final int unHealthy = 70;
        final int moderate = 80;
        final int healthy = 100;



        // set progress to text and progress bar
        double roundedValue = Math.round(overallHealthScore);

        if (overallHealthScore - Math.floor(overallHealthScore) >= 0.5) {
            roundedValue = Math.ceil(overallHealthScore);
        }
        txtOverallHealthScore.setText(String.valueOf((int)roundedValue)+"%");
        progressOverallHealthScore.setProgressPercentage(roundedValue, true);

        if(overallHealthScore<=unHealthy){
            progressOverallHealthScore.setProgressDrawableColor(getResources().getColor(R.color.red));
            txtOverallHealthScore.setTextColor(getResources().getColor(R.color.red));
        }else if (overallHealthScore<moderate){
            progressOverallHealthScore.setProgressDrawableColor(getResources().getColor(R.color.yellow));
            txtOverallHealthScore.setTextColor(getResources().getColor(R.color.yellow));
        }else if(overallHealthScore<=healthy){
            progressOverallHealthScore.setProgressDrawableColor(getResources().getColor(R.color.green));
            txtOverallHealthScore.setTextColor(getResources().getColor(R.color.green));
        }
    }

}