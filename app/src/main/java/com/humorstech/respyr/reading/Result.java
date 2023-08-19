package com.humorstech.respyr.reading;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import cz.msebera.android.httpclient.Header;
import me.relex.circleindicator.CircleIndicator2;
import pl.droidsonroids.gif.GifImageView;


public class Result extends AppCompatActivity {

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

    double blowScore=86;
    String  date, time;

    private ScrollView scrollView;
    final int YLifeStyle = 8199;
    ReferenceSheet referenceSheet = new ReferenceSheet(Result.this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        StatusBarColor statusBarColor= new StatusBarColor(  Result.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        txtLog=findViewById(R.id.txt_log);
        txtLog.setMovementMethod(new ScrollingMovementMethod());


        ImageButton buttonBack =findViewById(R.id.button_back);
        buttonBack.setVisibility(View.VISIBLE);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tempdashboard.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent!=null){
            // Retrieve the data from the Intent
            response = intent.getStringExtra("response");
            SP = intent.getStringExtra("SP");
            name = intent.getStringExtra("name");
            DP = intent.getStringExtra("DP");
            Beats = intent.getStringExtra("BPM");
            Breath = intent.getStringExtra("Breath");
            spo2 = intent.getStringExtra("SPO2");
            bpm1 = intent.getStringExtra("BPM1");
            bpm2 = intent.getStringExtra("BPM2");
            waterIntake = intent.getStringExtra("waterIntake");
            smokingUnits = intent.getStringExtra("smokingUnits");
            isTakenAlcohol = intent.getStringExtra("isTakenAlcohol");
            sleepHours = intent.getStringExtra("sleepHours");
            exerciseInMinutes = intent.getStringExtra("exerciseInMinutes");
            foodName = intent.getStringExtra("foodName");
            foodQuantity = intent.getStringExtra("foodQuantity");
            foodCount = intent.getStringExtra("foodCount");
            isHadBreakfast =intent.getStringExtra("isHadBreakfast");
            isHadLunch =intent.getStringExtra("isHadLunch");
            isHadDinner = intent.getStringExtra("isHadDinner");
            USER_ID = intent.getStringExtra("USER_ID");
            gender = intent.getStringExtra("gender");
            age = intent.getStringExtra("age");
            height = intent.getStringExtra("height");
            weight = intent.getStringExtra("weight");


            // Print the retrieved data
            System.out.println("---------------------final");
            System.out.println("Response: " + response);
            System.out.println("SP: " + SP);
            System.out.println("Name: " + name);
            System.out.println("DP: " + DP);
            System.out.println("Beats: " + Beats);
            System.out.println("Breath: " + Breath);
            System.out.println("SPO2: " + spo2);
            System.out.println("BPM1: " + bpm1);
            System.out.println("BPM2: " + bpm2);
            System.out.println("Water Intake: " + waterIntake);
            System.out.println("Smoking Units: " + smokingUnits);
            System.out.println("Is Taken Alcohol: " + isTakenAlcohol);
            System.out.println("Sleep Hours: " + sleepHours);
            System.out.println("Exercise in Minutes: " + exerciseInMinutes);
            System.out.println("Food Name: " + foodName);
            System.out.println("Food Quantity: " + foodQuantity);
            System.out.println("Food count: " + foodCount);
            System.out.println("Is Had Breakfast: " + isHadBreakfast);
            System.out.println("Is Had Lunch: " + isHadLunch);
            System.out.println("Is Had Dinner: " + isHadDinner);
            System.out.println("USER_ID: " + USER_ID);
            System.out.println("Gender: " + gender);
            System.out.println("Age: " + age);
            System.out.println("Height: " + height);
            System.out.println("Weight: " + weight);


        }

        txtLog.append(response+"\n");

        //  response="{\"data\":[{\"status\":1,\"ace\":\"0.17162892\",\"pp_press\":\"73\",\"etholpp\":\"10\",\"battry\":null,\"finaltemp\":\"31\",\"duration\":\"90\",\"rawmic\":null,\"bmhumid\":null,\"besthumid\":0,\"bpm\":null,\"valpress\":null,\"peak_press\":null,\"nopp\":null,\"cap\":\"0\",\"val1820\":\"180\",\"valFinal1820\":\"181\",\"MVacetone\":3.912}]}";
        decodeJson(response);


        scrollView =findViewById(R.id.result_scroll);
        GifImageView gifImageView =findViewById(R.id.image_swipe_up);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // Scroll position has changed
                int scrollY = scrollView.getScrollY(); // Current vertical scroll position
                System.out.println(scrollY);
                //7512

                if (scrollY >=1846){
                    gifImageView.setVisibility(View.GONE);
                }else{
                    gifImageView.setVisibility(View.VISIBLE);
                }
            }
        });




    }


    private void decodeJson(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            JSONObject dataObject = dataArray.getJSONObject(0);
            double acetone = dataObject.getDouble("ace");
            double ethanol = dataObject.getDouble("etholpp");
            double MVacetone = dataObject.getDouble("MVacetone");

            blow = dataObject.getDouble("cap");



//            double val1820 = dataObject.getDouble("val1820");
//            double final1820 = dataObject.getDouble("valFinal1820");
//            double difference = final1820-val1820;
//
//            double finalAcetone = (difference*5) / 1023;
//            finalAcetone = finalAcetone*1000;
//
//            double finalEthanol = (ethanol / 0.041);
//            finalEthanol = (finalEthanol / 1000);



//
//            txtLog.append("Difference :"+String.valueOf(difference)+"\n");
//            txtLog.append("Final Acetone :"+String.valueOf(finalAcetone)+"\n");
//            txtLog.append("Final val :"+String.valueOf(finalEthanol)+"\n");


            //      fetchDiabeticScore("120", "74", "98","26", "98", String.valueOf(MVacetone), String.valueOf(ethanol),String.valueOf(blow));
            fetchDiabeticScore(SP, DP, Beats,age, "98", String.valueOf(MVacetone), String.valueOf(ethanol),String.valueOf(blow));


        }catch (Exception e){
            Log.d(TAG, e.getMessage());

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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


        //   String url = "https://respyr.in/predict?systolic="+SP+"&diastolic="+DP+"&age="+age+"&heart_rate="+heartRate+"&spo2="+spo2+"&acetone="+acetone+"&ethnol="+ethanol+"&Blow="+blow;

        //   Log.d(TAG, "fetchDiabeticScore: "+url);

        storeParamsVitals(params);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get( "https://humorstech.com/humors/json_curl/db_vital.php",params,

                new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                          Dialogs.showLoadingDialog(Result.this, "Please wait....");
                          System.out.println("DB Vital params-------->"+params);

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {



                        String response2 = new String(responseBody);

                        txtLog.append(response2+"\n");

                        System.out.println("Db vital on success-------->"+response2);

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


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                fetAllResult(
                                        activity_level,String.valueOf(diabeticScore), isHadBreakfast,
                                        isHadLunch, isHadDinner, String.valueOf(overallVitalScore) ,
                                        height, age, weight,gender,
                                        foodName,exerciseInMinutes, waterIntake , isTakenAlcohol,
                                        smokingUnits,sleepHours, getCurrentDayName(),  "veg");
                            }


                        }catch (JSONException e){
                            Log.d(TAG, e.getMessage());
                            System.out.println("db vital exception-------->"+ e.getMessage());
                            txtLog.append( e.getMessage()+"\n");
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(Result.this, String.valueOf(error), Toast.LENGTH_SHORT).show();

                        System.out.println("response "+String.valueOf(error));
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


//        https://humorstech.com/humors/json_curl/life_style.php?
//        // activity_level=sedentary&DB_Score=80&breakfast=no&lunch=yes&dinner=yes
//        // &VT_Score=90&height=187&age=33&weight=45&gender=male&food_intake=2&
//        // food_name=%27food_name_0=Rice,food_name_1=dal%27&food_quantity=%27
//        // food_quantity_0=1000,food_quantity_1=1000%27
//        // &exercise_hours=20&sleep_hours=8&water_consumption=1
//        // 500&alcohol_consumption=0&smoking_units=0&day=Tuesday&type=veg

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

        storeParamsDB(params);

        txtLog.append(String.valueOf(params)+"\n");
        // String dummyURL = "https://humorstech.com/humors/json_curl/life_style.php?activity_level=sedentary&DB_Score=80&breakfast=no&lunch=yes&dinner=yes&VT_Score=90&height=187&age=33&weight=45&gender=male&food_intake=2&food_name=food_name_0=Rice,food_name_1=dal&food_quantity=food_quantity_0=1000,food_quantity_1=1000&exercise_hours=20&sleep_hours=8&water_consumption=1500&alcohol_consumption=0&smoking_units=0&day=Tuesday&type=veg";


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://humorstech.com/humors/json_curl/life_style.php",params, new AsyncHttpResponseHandler() {
            //   client.get(dummyURL, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

                System.out.println("Params----------------------->"+params);
                Log.d(TAG, "----------------->"+String.valueOf(params));

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"

                //   Dialogs.hideLoadingDialog();

                String response3 = new String(response);
                txtLog.append(response3+"\n");
                System.out.println("Final response----------------------->"+response);
                System.out.println(response3);
                decodeResultData(response3);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });






    }

    private void decodeResultData(String jsonData){
//        String jsonData = "{\n" +
//                "  \"data\": [\n" +
//                "    {\n" +
//                "      \"healthScore\": \"78\",\n" +
//                "      \"diabeticScore\": 95,\n" +
//                "      \"vitalScore\": 98,\n" +
//                "      \"bmi\": \"25.4\",\n" +
//                "      \"bmr\": \"1500\",\n" +
//                "      \"BMISuggestions\": \"Maintain a healthy weight\",\n" +
//                "      \"curr_cal\": 2000,\n" +
//                "      \"curr_car\": 150,\n" +
//                "      \"curr_pro\": 60,\n" +
//                "      \"curr_fat\": 50,\n" +
//                "      \"curr_fib\": 25,\n" +
//                "      \"reco_cal\": 1800,\n" +
//                "      \"reco_car\": 120,\n" +
//                "      \"reco_pro\": 70,\n" +
//                "      \"reco_fat\": 40,\n" +
//                "      \"reco_fib\": 30,\n" +
//                "      \"NutriScore_cal\": 8,\n" +
//                "      \"NutriScore_car\": 6,\n" +
//                "      \"NutriScore_pro\": 7,\n" +
//                "      \"NutriScore_fat\": 5,\n" +
//                "      \"NutriScore_fib\": 6,\n" +
//                "      \"OverallNutrientScore\": 32,\n" +
//                "      \"NutriSug_cal\": 40,\n" +
//                "      \"NutriSug_car\": 20,\n" +
//                "      \"NutriSug_pro\": 10,\n" +
//                "      \"NutriSug_fat\": 5,\n" +
//                "      \"NutriSug_fib\": 15,\n" +
//                "      \"OverallLifeStyleScore\": 90,\n" +
//                "      \"DetailedLifeStyleScore_nutscore\": 12,\n" +
//                "      \"DetailedLifeStyleScore_excscore\": 15,\n" +
//                "      \"DetailedLifeStyleScore_waterscore\": 8,\n" +
//                "      \"DetailedLifeStyleScore_alcoscore\": 3,\n" +
//                "      \"DetailedLifeStyleScore_smokescore\": 2,\n" +
//                "      \"detailedLifeStyleScore_sleep_score\": 7,\n" +
//                "      \"detailedLifeStyleScore_lfstyle_score\": 18,\n" +
//                "      \"LifeStyleSuggestions\": \"Improve sleep quality\",\n" +
//                "      \"FoodTimingSugessions\": \"Maintain regular meal timings\",\n" +
//                "      \"exercisesDetails\": \"Cardio and strength training\",\n" +
//                "      \"breakfast_sugs\": \"Include whole grains and fruits\",\n" +
//                "      \"lunch_sug\": \"Opt for lean protein and vegetables\",\n" +
//                "      \"dinner_sug\": \"Choose light and balanced options\"\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}\n";

// Create a Gson object
        Gson gson = new Gson();

// Parse the JSON data
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        JsonArray dataArray = jsonObject.getAsJsonArray("data");

// Iterate over the data array
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



                // Get the SharedPreferences instance
                SharedPreferences sharedPreferences = getSharedPreferences("result_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                txtLog.append("Diabetic2 -->"+String.valueOf(diabeticScore)+"\n");

                // Store the data
                // Store the data as strings
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


                // Apply the changes
                editor.apply();



//            String NutriScore_cal = item.get("NutriScore_cal").getAsString();
//            String NutriScore_car = item.get("NutriScore_car").getAsString();
//            String NutriScore_pro = item.get("NutriScore_pro").getAsString();
//            String NutriScore_fat = item.get("NutriScore_fat").getAsString();
//            String NutriScore_fib = item.get("NutriScore_fib").getAsString();
//            String OverallNutrientScore = item.get("OverallNutrientScore").getAsString();
//            String NutriSug_cal = item.get("NutriSug_cal").getAsString();
//            String NutriSug_car = item.get("NutriSug_car").getAsString();
//            String NutriSug_pro = item.get("NutriSug_pro").getAsString();
//            String NutriSug_fat = item.get("NutriSug_fat").getAsString();
//            String NutriSug_fib = item.get("NutriSug_fib").getAsString();
//


//
//            String DetailedLifeStyleScore_nutscore = item.get("DetailedLifeStyleScore_nutscore").getAsString();
//            String DetailedLifeStyleScore_excscore = item.get("DetailedLifeStyleScore_excscore").getAsString();
//            String DetailedLifeStyleScore_waterscore = item.get("DetailedLifeStyleScore_waterscore").getAsString();
//            String DetailedLifeStyleScore_alcoscore = item.get("DetailedLifeStyleScore_alcoscore").getAsString();
//            String DetailedLifeStyleScore_smokescore = item.get("DetailedLifeStyleScore_smokescore").getAsString();
//            String detailedLifeStyleScore_sleep_score = item.get("detailedLifeStyleScore_sleep_score").getAsString();


//            String LifeStyleSuggestions = item.get("LifeStyleSuggestions").getAsString();
//            String FoodTimingSugessions = item.get("FoodTimingSugessions").getAsString();
//            String exercisesDetails = item.get("exercisesDetails").getAsString();
//            String breakfast_sugs = item.get("breakfast_sugs").getAsString();
//            String lunch_sug = item.get("lunch_sug").getAsString();
//            String dinner_sug = item.get("dinner_sug").getAsString();

//            // Print the values
//            System.out.println("Health Score: " + healthScore);
//            System.out.println("Diabetic Score: " + diabeticScore);
//            System.out.println("Vital Score: " + vitalScore);
//            System.out.println("BMI: " + bmi);
//            System.out.println("BMR: " + bmr);
//            System.out.println("BMI Suggestions: " + BMISuggestions);
//            System.out.println("Current Calories: " + curr_cal);
//            System.out.println("Current Carbohydrates: " + curr_car);
//            System.out.println("Current Protein: " + curr_pro);
//            System.out.println("Current Fat: " + curr_fat);
//            System.out.println("Current Fiber: " + curr_fib);
//            System.out.println("Recommended Calories: " + reco_cal);
//            System.out.println("Recommended Carbohydrates: " + reco_car);
//            System.out.println("Recommended Protein: " + reco_pro);
//            System.out.println("Recommended Fat: " + reco_fat);
//            System.out.println("Recommended Fiber: " + reco_fib);
//            System.out.println("NutriScore Calories: " + NutriScore_cal);
//            System.out.println("NutriScore Carbohydrates: " + NutriScore_car);
//            System.out.println("NutriScore Protein: " + NutriScore_pro);
//            System.out.println("NutriScore Fat: " + NutriScore_fat);
//            System.out.println("NutriScore Fiber: " + NutriScore_fib);
//            System.out.println("Overall Nutrient Score: " + OverallNutrientScore);
//            System.out.println("NutriSug Calories: " + NutriSug_cal);
//            System.out.println("NutriSug Carbohydrates: " + NutriSug_car);
//            System.out.println("NutriSug Protein: " + NutriSug_pro);
//            System.out.println("NutriSug Fat: " + NutriSug_fat);
//            System.out.println("NutriSug Fiber: " + NutriSug_fib);
//            System.out.println("Overall Lifestyle Score: " + OverallLifeStyleScore);
//            System.out.println("Detailed Lifestyle Score - Nutrient Score: " + DetailedLifeStyleScore_nutscore);
//            System.out.println("Detailed Lifestyle Score - Exercise Score: " + DetailedLifeStyleScore_excscore);
//            System.out.println("Detailed Lifestyle Score - Water Score: " + DetailedLifeStyleScore_waterscore);
//            System.out.println("Detailed Lifestyle Score - Alcohol Score: " + DetailedLifeStyleScore_alcoscore);
//            System.out.println("Detailed Lifestyle Score - Smoking Score: " + DetailedLifeStyleScore_smokescore);
//            System.out.println("Detailed Lifestyle Score - Sleep Score: " + detailedLifeStyleScore_sleep_score);
//            System.out.println("Detailed Lifestyle Score - Lifestyle Score: " + detailedLifeStyleScore_lfstyle_score);
//            System.out.println("Lifestyle Suggestions: " + LifeStyleSuggestions);
//            System.out.println("Food Timing Suggestions: " + FoodTimingSugessions);
//            System.out.println("Exercise Details: " + exercisesDetails);
//            System.out.println("Breakfast Suggestions: " + breakfast_sugs);
//            System.out.println("Lunch Suggestions: " + lunch_sug);
//            System.out.println("Dinner Suggestions: " + dinner_sug);



                init();
            }

        }


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


         Dialogs.hideLoadingDialog();
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


        String profileName1 = profileName + ",<br />Your <b><font color=\"#252525\">" + "Diabetic Score" + "</font></b> is";
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
        textView.setText(String.valueOf((int)score)+"%");

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
        //set profile name with message
        String profileName = name;
        String profileName1 = profileName + ",<br />Your <b><font color=\"#252525\">" + "Nutrition Score" + "</font></b> is";
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

        StatusBarColor statusBarColor= new StatusBarColor(Result.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));


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

    private void vitalData() {
        LineChart lineChart = findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 80));
        entries.add(new Entry(1, 98));
        entries.add(new Entry(2, 63));
        entries.add(new Entry(3, (float) vitalScore));

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM");
        String formattedDate = dateFormat.format(currentDate);


        String[] dates = {"22 Jun","22 Jun","22 Jun",formattedDate};

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = (int) value;
                return dates[index];
            }
        });
        xAxis.setTextSize(8f); // Set label size

        Typeface font = ResourcesCompat.getFont(this, R.font.roboto); // Load the custom font
        xAxis.setTypeface(font); // Apply font family to the labels

        LineDataSet dataSet = new LineDataSet(entries, "Line Data");
        dataSet.setColor(Color.parseColor("#535359")); // Set line color
        dataSet.setLineWidth(1.5f); // Set line thickness in pixels
        dataSet.setDrawCircles(false); // Hide data points (dots)
        dataSet.setDrawValues(false); // Hide values on the chart

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false); // Hide left Y-axis line
        leftAxis.setDrawGridLines(false); // Hide left Y-axis grid lines
        leftAxis.setAxisMinimum(0f); // Set minimum value for the Y-axis
        leftAxis.setAxisMaximum(100f); // Set maximum value for the Y-axis
        leftAxis.setGranularity(20f); // Set granularity for the Y-axis
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false); // Disable the right Y-axis

        lineChart.getXAxis().setDrawGridLines(false); // Hide X-axis grid lines
        lineChart.getXAxis().setAxisLineWidth(2f); // Set X-axis line thickness

        lineChart.getDescription().setEnabled(false); // Remove description label
        lineChart.getLegend().setEnabled(false); // Hide legends
        lineChart.invalidate();
    }

//    private void performCalories(){
//
//        /// calories
//        TextView txtCaloriesScore = findViewById(R.id.txt_calories_score);
//        TextView txtActualCalories = findViewById(R.id.txt_calories_actual);
//        TextView txtRecommendedCalories = findViewById(R.id.txt_calories_recomoneded);
//        TextView txtCaloriesDifference = findViewById(R.id.txt_calories_difference);
//
//
//
//        double actualCalories = curr_cal;
//        double recommendedCalories  =reco_cal;
//        double caloriesScore = (actualCalories / recommendedCalories) * 100;
//
//        CaloriesAnalysis.performTexts(getApplicationContext(),
//                caloriesScore,
//                name,
//                actualCalories,
//                recommendedCalories,
//                txtCaloriesScore,
//                txtActualCalories,
//                txtRecommendedCalories,
//                txtCaloriesDifference,
//                1
//        );
//
//        // carbo hydrates
//        TextView txtCarboHydratesScore = findViewById(R.id.txt_carbo_hydrate_score);
//        TextView txtActualCarboHydrates = findViewById(R.id.txt_carbo_hydrate_actual);
//        TextView txtRecommendedCarboHydrates = findViewById(R.id.txt_carbo_hydrate_recommended);
//        TextView txtCarboHydratesDifference = findViewById(R.id.txt_carbo_hydrate_difference);
//
//
//
//        double actualCarboHydrates = curr_car;
//        double recommendedCarboHydrates  = reco_car;
//        double caloriesCarboHydrates = (actualCarboHydrates / recommendedCarboHydrates) * 100;
//
//        CaloriesAnalysis.performTexts(getApplicationContext(),
//                caloriesCarboHydrates,
//                name,
//                actualCarboHydrates,
//                recommendedCarboHydrates,
//                txtCarboHydratesScore,
//                txtActualCarboHydrates,
//                txtRecommendedCarboHydrates,
//                txtCarboHydratesDifference,
//                2
//        );
//
//        // protein
//        TextView txtProteinScore = findViewById(R.id.txt_protein_score);
//        TextView txtActualProtein = findViewById(R.id.txt_actual_protein);
//        TextView txtRecommendedProtein = findViewById(R.id.txt_recommended_protein);
//        TextView txtDifferenceProtein = findViewById(R.id.txt_difference_protein);
//
//
//        double actualProtein = curr_pro;
//        double recommendedProtein  = reco_pro;
//        double scoreProtein = (actualProtein / recommendedProtein) * 100;
//
//        CaloriesAnalysis.performTexts(getApplicationContext(),
//                scoreProtein,
//                name,
//                actualProtein,
//                recommendedProtein,
//                txtProteinScore,
//                txtActualProtein,
//                txtRecommendedProtein,
//                txtDifferenceProtein,
//                3
//        );
//
//        // fats
//        TextView txtFatsScore = findViewById(R.id.txt_fats_score);
//        TextView txtActualFats = findViewById(R.id.txt_fats_actual);
//        TextView txtRecommendedFats= findViewById(R.id.txt_fats_recommended);
//        TextView txtDifferenceFats = findViewById(R.id.txt_fats_difference);
//
//
//        double actualFats=  curr_fat;
//        double recommendedFats  = reco_fat;
//        double scoreFats = (actualFats / recommendedFats) * 100;
//
//        CaloriesAnalysis.performTexts(getApplicationContext(),
//                scoreFats,
//                name,
//                actualFats,
//                recommendedFats,
//                txtFatsScore,
//                txtActualFats,
//                txtRecommendedFats,
//                txtDifferenceFats,
//                4
//        );
//
//        // fibre
//        TextView txtFibreScore = findViewById(R.id.txt_fibre_score);
//        TextView txtActualFibre = findViewById(R.id.txt_fibre_actual);
//        TextView txtRecommendedFibre= findViewById(R.id.txt_fibre_recommended);
//        TextView txtDifferenceFibre= findViewById(R.id.txt_fibre_difference);
//
//
//        double actualFibre=  curr_fib;
//        double recommendedFibre = reco_fib ;
//        double scoreFibre = (actualFibre / recommendedFibre) * 100;
//
//        CaloriesAnalysis.performTexts(getApplicationContext(),
//                scoreFibre,
//                name,
//                actualFibre,
//                recommendedFibre,
//                txtFibreScore,
//                txtActualFibre,
//                txtRecommendedFibre,
//                txtDifferenceFibre,
//                5
//        );
//
//
//
//        /// perform progress
//        //Buttons
//        TextView btnCalories, btnCarbohydrate, btnProtein, btnFats, btnFiber;
//        btnCalories=findViewById(R.id.button_calories);
//        btnCarbohydrate=findViewById(R.id.button_carbohydrate);
//        btnProtein=findViewById(R.id.button_proteins);
//        btnFats=findViewById(R.id.button_fats);
//        btnFiber=findViewById(R.id.button_fibre);
//
//        // Layouts
//        RelativeLayout layoutCalories =findViewById(R.id.layout_calories_card);
//        RelativeLayout layoutCarboHydrate =findViewById(R.id.layout_carbohydrate_card);
//        RelativeLayout layoutProtein=findViewById(R.id.layout_protein_card);
//        RelativeLayout layoutFats=findViewById(R.id.layout_fats_card);
//        RelativeLayout layoutFiber=findViewById(R.id.layout_fiber_card);
//
//
//        // Circular Progress
//        CircularProgressIndicator p1=findViewById(R.id.p1);
//        CircularProgressIndicator p2=findViewById(R.id.p2);
//        CircularProgressIndicator p3=findViewById(R.id.p3);
//        CircularProgressIndicator p4=findViewById(R.id.p4);
//        CircularProgressIndicator p5=findViewById(R.id.p5);
//        CircularProgressIndicator p6=findViewById(R.id.p6);
//        CircularProgressIndicator p7=findViewById(R.id.p7);
//        CircularProgressIndicator p8=findViewById(R.id.p8);
//        CircularProgressIndicator p9=findViewById(R.id.p9);
//        CircularProgressIndicator p10=findViewById(R.id.p10);
//        CircularProgressIndicator p11=findViewById(R.id.p11);
//        CircularProgressIndicator p12=findViewById(R.id.p12);
//        CircularProgressIndicator p13=findViewById(R.id.p13);
//        CircularProgressIndicator p14=findViewById(R.id.p14);
//        CircularProgressIndicator p15=findViewById(R.id.p15);
//        CircularProgressIndicator p16=findViewById(R.id.p16);
//        CircularProgressIndicator p17=findViewById(R.id.p17);
//        CircularProgressIndicator p18=findViewById(R.id.p18);
//        CircularProgressIndicator p19=findViewById(R.id.p19);
//        CircularProgressIndicator p20=findViewById(R.id.p20);
//        CircularProgressIndicator p21=findViewById(R.id.p21);
//        CircularProgressIndicator p22=findViewById(R.id.p22);
//        CircularProgressIndicator p23=findViewById(R.id.p23);
//        CircularProgressIndicator p24=findViewById(R.id.p24);
//        CircularProgressIndicator p25=findViewById(R.id.p25);
//
//
//        // default
//        CaloriesAnalysis.performButtons(getApplicationContext(),1,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
//        CaloriesAnalysis.performLayouts(getApplicationContext(),1,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
//
//
//        CaloriesAnalysis.setProgress(getApplicationContext(),1,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p1,p2,p3,p4,p5);
//
//        btnCalories.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                CaloriesAnalysis.performButtons(getApplicationContext(),1,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
//                CaloriesAnalysis.performLayouts(getApplicationContext(),1,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
//                CaloriesAnalysis.setProgress(getApplicationContext(),1,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p1,p2,p3,p4,p5);
//
//            }
//        });
//        btnCarbohydrate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CaloriesAnalysis.performButtons(getApplicationContext(),2,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
//                CaloriesAnalysis.performLayouts(getApplicationContext(),2,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
//                CaloriesAnalysis.setProgress(getApplicationContext(),2,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p6,p7,p8,p9,p10);
//
//            }
//        });
//        btnProtein.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CaloriesAnalysis.performButtons(getApplicationContext(),3,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
//                CaloriesAnalysis.performLayouts(getApplicationContext(),3,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
//                CaloriesAnalysis.setProgress(getApplicationContext(),3,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p11,p12,p13,p14,p15);
//            }
//        });
//        btnFats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CaloriesAnalysis.performButtons(getApplicationContext(),4,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
//                CaloriesAnalysis.performLayouts(getApplicationContext(),4,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
//                CaloriesAnalysis.setProgress(getApplicationContext(),4,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p16,p17,p18,p19,p20);
//            }
//        });
//        btnFiber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CaloriesAnalysis.performButtons(getApplicationContext(),5,btnCalories,btnCarbohydrate,btnProtein,btnFats,btnFiber);
//                CaloriesAnalysis.performLayouts(getApplicationContext(),5,layoutCalories,layoutCarboHydrate,layoutProtein,layoutFats,layoutFiber);
//                CaloriesAnalysis.setProgress(getApplicationContext(),5,(int)caloriesScore,(int)caloriesCarboHydrates,(int)scoreProtein,(int)scoreFats,(int)scoreFibre,p21,p22,p23,p24,p25);
//            }
//        });
//
//        Button buttonViewFoodBreakup=findViewById(R.id.button_view_food_breakup);
//        buttonViewFoodBreakup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), FoodBreakUP.class);
//                startActivity(intent);
//            }
//        });
//
//
//    }

//    private void performCalories(){
//        Spinner spinner = findViewById(R.id.calories_spinner);
//
//        // Create an ArrayAdapter without any initial data
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.calories_spinner_item);
//
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(R.layout.colories_spinner_dropdown_item);
//
//
//        // Add items to the adapter
//        adapter.add("Calories");
//        adapter.add("Carbohydrate");
//        adapter.add("Proteins");
//        adapter.add("Fats");
//        adapter.add("Fibre");
//
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
//    }

    private void caloriesChart() {
        BarChart barChart = findViewById(R.id.coloriesChart);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 230f));
        entries.add(new BarEntry(1, 600f));
        entries.add(new BarEntry(2, 230f));
        entries.add(new BarEntry(3, 1300f));
        entries.add(new BarEntry(4, 130f));
        entries.add(new BarEntry(5, 240f));

        int barColor = Color.parseColor("#252525"); // Custom color for columns

        BarDataSet barDataSet = new BarDataSet(entries, "Bar Data Set");
        barDataSet.setColor(barColor);
        barDataSet.setValueTextSize(8f); // Set the text size for values above bars

        Typeface customTypeface = ResourcesCompat.getFont(this, R.font.roboto);
        barDataSet.setValueTypeface(customTypeface); // Set the typeface for the value labels

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.65f); // Set the width of the bars
        barData.setDrawValues(true); // Show values above bars

        barChart.setData(barData);

        // Customize the appearance of the chart
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setAxisLineColor(Color.parseColor("#252525")); // Set the x-axis line color
        xAxis.setAxisLineWidth(1f); // Set the x-axis line thickness
        xAxis.setTextSize(8f); // Set the text size for x-axis labels

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false); // Show grid lines
        yAxisLeft.setGridLineWidth(1f); // Set grid line width
        yAxisLeft.setDrawAxisLine(false); // Hide the left side axis line
        yAxisLeft.setTextSize(8f); // Set the text size for y-axis labels

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false); // Hide the right side axis line
        yAxisRight.setDrawLabels(false); // Hide the right side labels

        // Set font family for the entire chart
        barChart.setExtraTopOffset(10f); // Add some extra space above the chart to accommodate the value labels

        // Add the labels using ArrayList and loop
        ArrayList<String> labels = new ArrayList<>();
        labels.add("12 May");
        labels.add("13 May");
        labels.add("14 May");
        labels.add("15 May");
        labels.add("16 May");
        labels.add("17 May");
        labels.add("18 May");

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels)); // Set the x-axis labels

        barChart.invalidate();
    }





    @SuppressLint("SetTextI18n")
    private void performBMI(){
        ImageView imgBMI =findViewById(R.id.result_page_image_bmi);
        TextView textViewBMIProfileName =findViewById(R.id.result_page_txt_profile_name_bmi);
        TextView textViewBMIValue =findViewById(R.id.result_page_txt_value_bmi);
        TextView textViewBMIMeaning =findViewById(R.id.result_page_txt_bmi_meaning);
        TextView textViewBMISuggestion1 =findViewById(R.id.result_page_txt_suggestion1_bmi);
        TextView textViewBMISuggestion2 =findViewById(R.id.result_page_txt_bmi_suggestion2);
        Button buttonViewBMISuggestion =findViewById(R.id.result_page_button_view_suggestion_bmi);

        BMISuggestions = name +", " + BMISuggestions;
        String profileName = name;

        //set profile name with message
        String profileName1 = profileName+", <br/>You're <b>BMI score </b>is";
        Spanned newProfileName = Html.fromHtml(profileName1);
        textViewBMIProfileName.setText(newProfileName);


        // Create a HashMap to store the BMI data
        Map<String, Object> bmiData = new HashMap<>();
        bmiData.put("BMI", bmi);
//        bmiData.put("BMR", BMR);
        bmiData.put("BMI Suggestions", BMISuggestions);


        // perform all textview set texes and imageview
        BMIAnalysis.performBMIOperations(getApplicationContext(),bmi, textViewBMIValue, textViewBMIMeaning,imgBMI);

        // set bmi suggestion
        String[] splitString = BMISuggestions.split("\\.", 2);
        textViewBMISuggestion1.setText(splitString[0]);
        textViewBMISuggestion2.setText(profileName+", "+BMISuggestions);

        buttonViewBMISuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Suggestion.class);
                startActivity(intent);
            }
        });
    }

    private void storeParamsVitals(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://humorstech.com/humors/json_curl/db_vital2.php",params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        });
    }

    private void storeParamsDB(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://humorstech.com/humors/json_curl/life_style2.php",params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        });
    }

}


