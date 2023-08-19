package com.humorstech.respyr.authentication.profile_creation.lifestyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.HTTP_URLS;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.profile_creation.medical_data.BloodTest;
import com.humorstech.respyr.utills.IntentUtils;
import com.humorstech.respyr.utills.LifeStyleScoreUtils;
import com.humorstech.respyr.utills.LoginUtils;
import com.humorstech.respyr.utills.ProfileCreationData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class LifeStyleScore extends AppCompatActivity {


    // login credentials
    private String loginId,profileId;


    // BMI data
    private String userGender, userDOB, userAge, userHeight, userWeight;

    // LifeStyleData
    private String waterConsumption, alcohol, smoking, exercise, nonVeg, foodIntake, foodName,breakfast,dinner,lunch, foodQuantity, sleepHours, mentalConditions;


    private RadioButton star1,star2,star3,star4, star5;
    private Button buttonNext, buttonBack;

    private TextView txtLifeStyleScore;
    private TextView txtLifeStyleMessage;
    private TextView txtLifeStyleSubMessage;

    private double OverallLifeStyleScore;

    private static final String TAG = "LifeStyleScore";

    private TextView txtLog;

    private ShimmerFrameLayout shimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style_score);

        StatusBarColor statusBarColor = new StatusBarColor(LifeStyleScore.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();
    }

    private void init(){
        init_vars();
        getData();

        onClicks();
    }
    private void init_vars(){
        buttonNext=findViewById(R.id.button_next);
        txtLifeStyleScore=findViewById(R.id.txt_life_style_score);
        txtLifeStyleMessage=findViewById(R.id.txt_life_style_message);
        txtLifeStyleSubMessage=findViewById(R.id.txt_life_style_sub_message);
        txtLog=findViewById(R.id.txt_log);
        txtLog.setMovementMethod(new ScrollingMovementMethod());

        shimmerLayout = findViewById(R.id.shimmerLayout);
    }
    private void getData(){

        SharedPreferences sharedPreferences2 = getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
        loginId = sharedPreferences2.getString(LoginUtils.LOGIN_ID, "0");

        SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
        profileId = sharedPreferences.getString(ProfileCreationData.PROFILE_ID, "0");
        userGender = sharedPreferences.getString(ProfileCreationData.GENDER, "male");
        userDOB = sharedPreferences.getString(ProfileCreationData.DOB, "0");
        userAge = sharedPreferences.getString(ProfileCreationData.AGE, "25");
        userHeight = sharedPreferences.getString(ProfileCreationData.HEIGHT, "180");
        userWeight = sharedPreferences.getString(ProfileCreationData.WEIGHT, "65");
        mentalConditions = sharedPreferences.getString(ProfileCreationData.MENTAL_CONDITIONS, "65");

        txtLog.append("Gender : " + userGender + "\n");
        txtLog.append("Date of Birth : " + userDOB + "\n");
        txtLog.append("Age : " + userAge + "\n");
        txtLog.append("Height : " + userHeight + "\n");
        txtLog.append("Weight : " + userWeight + "\n");


        waterConsumption = sharedPreferences.getString(ProfileCreationData.WATER_CONSUMPTION, "5");
        sleepHours = sharedPreferences.getString(ProfileCreationData.SLEEP_HOURS, "8");
        alcohol = sharedPreferences.getString(ProfileCreationData.ALCOHOL, "0");
        smoking = sharedPreferences.getString(ProfileCreationData.SMOKING, "0");
        exercise = sharedPreferences.getString(ProfileCreationData.EXERCISE, "Sedentary");
        nonVeg = sharedPreferences.getString(ProfileCreationData.NOV_VEG, "veg");
        foodIntake = sharedPreferences.getString(ProfileCreationData.FOOD_INTAKE, "1");
        foodName = sharedPreferences.getString(ProfileCreationData.FOOD_NAME, "food_name_0=Rice");
        foodQuantity = sharedPreferences.getString(ProfileCreationData.FOOD_QUANTITY, "food_quantity_0=500");
        breakfast = sharedPreferences.getString(ProfileCreationData.BREAKFAST, "true");
        lunch = sharedPreferences.getString(ProfileCreationData.LUNCH, "true");
        dinner = sharedPreferences.getString(ProfileCreationData.DINNER, "true");

        // Append the values to the TextView
        txtLog.append("Water Consumption: " + waterConsumption + "\n");
        txtLog.append("Alcohol: " + alcohol + "\n");
        txtLog.append("Smoking: " + smoking + "\n");
        txtLog.append("Exercise: " + exercise + "\n");
        txtLog.append("Non-Veg: " + nonVeg + "\n");
        txtLog.append("Food Intake: " + foodIntake + "\n");
        txtLog.append("Food Name: " + foodName + "\n");
        txtLog.append("Food Quantity: " + foodQuantity + "\n");
        txtLog.append("Breakfast: " + breakfast + "\n");
        txtLog.append("Lunch: " + lunch + "\n");
        txtLog.append("Dinner: " + dinner + "\n");

        fetchLifeStyleScore();
    }

    private void setRating(int rating){

        star1=findViewById(R.id.star1);
        star2=findViewById(R.id.star2);
        star3=findViewById(R.id.star3);
        star4=findViewById(R.id.star4);
        star5=findViewById(R.id.star5);

        switch (rating){
            case 1 : star1.setChecked(true); star2.setChecked(false); star3.setChecked(false); star4.setChecked(false); star5.setChecked(false); break;
            case 2 : star1.setChecked(true); star2.setChecked(true); star3.setChecked(false); star4.setChecked(false); star5.setChecked(false); break;
            case 3 : star1.setChecked(true); star2.setChecked(true); star3.setChecked(true); star4.setChecked(false); star5.setChecked(false); break;
            case 4 : star1.setChecked(true); star2.setChecked(true); star3.setChecked(true); star4.setChecked(true); star5.setChecked(false); break;
            case 5 : star1.setChecked(true); star2.setChecked(true); star3.setChecked(true); star4.setChecked(true); star5.setChecked(true); break;
        }
    }
    private void onClicks(){
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addHobbiesDataToServer();
            }
        });
    }

    private void addHobbiesDataToServer(){

        RequestParams params = new RequestParams();
        params.put("login_id",loginId);
        params.put("profile_id",profileId);
        params.put("water_consume",waterConsumption);
        params.put("alcoholic",alcohol);
        params.put("smoking",smoking);
        params.put("non_veg",nonVeg);
        params.put("exercise",exercise);
        params.put("sleep_hours",sleepHours);
        params.put("mental_conditions",mentalConditions);
        params.put("life_style_score",OverallLifeStyleScore);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.addHobbies,params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialogs.showLoadingDialog(LifeStyleScore.this, "Please wait...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dialogs.hideLoadingDialog();
                        String response = new String(responseBody).trim();
                        Log.d(TAG, response);
                        if (response.equals("inserted")) {
                            Intent intent = new Intent(getApplicationContext(), BloodTest.class);
                            intent.putExtra(IntentUtils.profileId, profileId);
                            startActivity(intent);
                        }
                    }
                },2000);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    private void fetchLifeStyleScore(){


        RequestParams params = new RequestParams();
        params.put("activity_level", exercise);
        params.put("DB_Score", "100");
        params.put("breakfast", skipMeal(breakfast));
        params.put("lunch", skipMeal(lunch));
        params.put("dinner", skipMeal(dinner));
        params.put("VT_Score", "100");
        params.put("height", userHeight);
        params.put("age", userAge);
        params.put("weight", userWeight);
        params.put("gender", userGender);
        params.put("food_intake", foodIntake);
        params.put("food_name", foodName);
        params.put("food_quantity", foodQuantity);
        params.put("exercise_hours", 8);
        params.put("sleep_hours", sleepHours);
        params.put("water_consumption", waterConsumption);
        params.put("alcohol_consumption", "0");
        params.put("smoking_units", "0");
        params.put("day", "mon");
        params.put("type", "veg");

        System.out.println("Params"+params);

        txtLog.append("--------------------Params\n");
        txtLog.append(params +"\n");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://humorstech.com/humors/json_curl/life_style.php",params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // Start the shimmer animation
                shimmerLayout.startShimmer();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String response3 = new String(response);
                        txtLog.append("--------------------Response\n");
                        txtLog.append("response : \n");
                        txtLog.append(response3 +"\n");
                        decodeResultData(response3);
                    }
                },2500);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }
        });
    }

    private void decodeResultData(String jsonData){

        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
            JsonArray dataArray = jsonObject.getAsJsonArray("data");
            for (int i = 0; i < dataArray.size(); i++) {
                JsonObject item = dataArray.get(i).getAsJsonObject();


                double  healthScore = item.get("healthScore").getAsDouble();
                if (healthScore!=0){
                    OverallLifeStyleScore = item.get("OverallLifeStyleScore").getAsDouble();
                    performLifeStyleScore(OverallLifeStyleScore);
                }

            }

        }catch (JsonParseException e){
            shimmerLayout.hideShimmer();
            txtLog.append("--------------------Exception\n");
            txtLog.append(e.getMessage()+"\n");
            System.out.println("Exception"+e.getMessage());
        }

    }

    private void performLifeStyleScore(double OverallLifeStyleScore){
            double roundedValue = Math.round(OverallLifeStyleScore);
            if (OverallLifeStyleScore - Math.floor(OverallLifeStyleScore) >= 0.5) {
                roundedValue = Math.ceil(OverallLifeStyleScore);
            }
            int lifeStyleScoreNew = (int) roundedValue;
            double rating=Math.round(lifeStyleScoreNew / 20.0f);
            setRating((int)rating);

            txtLifeStyleScore.setText(String.valueOf(lifeStyleScoreNew));

            if(lifeStyleScoreNew<= LifeStyleScoreUtils.unHealthy){
                txtLifeStyleMessage.setText(LifeStyleScoreUtils.titles[2]);
                txtLifeStyleSubMessage.setText(LifeStyleScoreUtils.subTitles[2]);
            }else if (lifeStyleScoreNew< LifeStyleScoreUtils.moderate){
                txtLifeStyleMessage.setText(LifeStyleScoreUtils.titles[1]);
                txtLifeStyleSubMessage.setText(LifeStyleScoreUtils.subTitles[1]);
            }else if(lifeStyleScoreNew<= LifeStyleScoreUtils.healthy){
                txtLifeStyleMessage.setText(LifeStyleScoreUtils.titles[0]);
                txtLifeStyleSubMessage.setText(LifeStyleScoreUtils.subTitles[0]);
            }
        shimmerLayout.hideShimmer();
    }



    private String skipMeal(String meal){
        if (meal.equals("true")){
            return "yes";
        }else{
            return "no";
        }
    }

    private String performNonVeg(String nonVeg){
        if (nonVeg.equals("Regularly")){
            return "non_veg";
        }else if (nonVeg.equals("Occasionally")){
            return "non_veg";
        }else{
            return "veg";
        }
    }

}

