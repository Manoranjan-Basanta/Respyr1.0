package com.humorstech.respyr.daily_routine;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.HTTP_URLS;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.daily_routine.added.AddedFoodAdapter2;
import com.humorstech.respyr.daily_routine.added.AddedFoodData;
import com.humorstech.respyr.daily_routine.search.FoodDataBase;
import com.humorstech.respyr.reading.BeforeReading;
import com.humorstech.respyr.utills.ActiveProfile;
import com.humorstech.respyr.utills.FriendsReadingParameters;
import com.humorstech.respyr.utills.ReadingParameters;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import cz.msebera.android.httpclient.Header;

public class DailyRoutinForm extends AppCompatActivity {


    private String USER_ID;
    private String name,gender,age,height,weight;

    private int cigarettesCount=0;
    private int waterCount=0;
    private int exerciseCount=0;

    private boolean isBreakfast=true;
    private boolean isLunch=true;
    private boolean isDinner=true;

    private int alcoholConsumption=0;
    private int sleepHours=8;

    private final HashMap<String, String> foodItems = new HashMap<>();
    private final StringBuilder stringBuilder = new StringBuilder();
    private final StringBuilder stringBuilder2 = new StringBuilder();
    private int foodCount=0;


    private static final String TAG = "DailyRoutinForm";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_routin_form);
        StatusBarColor statusBarColor = new StatusBarColor(DailyRoutinForm.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

//        FoodDataBase dataBase=new FoodDataBase(getApplicationContext());
//        dataBase.clearTable();


        SharedPreferences sharedPreferences2 = getSharedPreferences("WATER", Context.MODE_PRIVATE);
        String wc = sharedPreferences2.getString("WC", "1");
        waterCount= Integer.parseInt(wc);

        init();

    }




    private void init(){
        activityDate();
        performMeal();
        performSmokeCounter();
        performWaterCounter();
        performExercise();
        performAlcohol();
        performSleepTiming();
        onClicks();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getCurrentDayName() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault());
    }


    @Override
    protected void onStart() {
        super.onStart();
        stringBuilder.setLength(0);
        stringBuilder2.setLength(0);
        performAddedFood();

        SharedPreferences sharedPreferences = getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(ActiveProfile.NAME, null);
        gender = sharedPreferences.getString(ActiveProfile.GENDER, null);
        age = sharedPreferences.getString(ActiveProfile.AGE, null);
        height = sharedPreferences.getString(ActiveProfile.HEIGHT, null);
        weight = sharedPreferences.getString(ActiveProfile.WEIGHT, null);

//        Intent intent = getIntent();
//        int i = intent.getIntExtra("data", 0);
//        if (i==1){
//            fetchProfileData();
//        }else if(i==2){
//            fetchFriendData();
//        }
    }
    private void performSleepTiming(){
        IndicatorSeekBar sleepSeekBar = findViewById(R.id.sleep_hour_bar);
        EditText etSleepHours=findViewById(R.id.et_hours);
        sleepSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                etSleepHours.setText(String.valueOf(seekParams.progress));
                sleepHours=seekParams.progress;
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void activityDate(){
        TextView activityDate =findViewById(R.id.txt_routine_date);

        // Get yesterday's date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,0);
        Date yesterday = calendar.getTime();

        // Format the date to your desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(yesterday);

        activityDate.setText("Today, \n" + formattedDate);

    }



    @SuppressLint("SetTextI18n")
    private void onClicks(){
        ImageButton buttonBack=findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> onBackPressed());

        Button buttonAddMeal=findViewById(R.id.button_add_meal);
        buttonAddMeal.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchMeal.class);
            startActivity(intent);
        });

        Button button_clear=findViewById(R.id.button_clear);
        button_clear.setOnClickListener(v -> {
                    FoodDataBase dataBase=new FoodDataBase(getApplicationContext());
                    dataBase.clearTable();
                    recreate();
        });

        Button buttonNext=findViewById(R.id.button_next);
        buttonNext.setOnClickListener(v -> {
            moveToBloodPressure();
        });
    }
    private void moveToBloodPressure(){
        SharedPreferences sharedPreferences = getSharedPreferences(ReadingParameters.TITLE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ReadingParameters.WATER_INTAKE, String.valueOf(waterCount));
        editor.putString(ReadingParameters.SMOKING_UNITS, String.valueOf(cigarettesCount));
        editor.putString(ReadingParameters.IS_TAKEN_ALCOHOL,  String.valueOf(alcoholConsumption));
        editor.putString(ReadingParameters.SLEEP_HOURS,  String.valueOf(sleepHours));
        editor.putString(ReadingParameters.EXERCISE_IN_MINUTES, String.valueOf(exerciseCount));
        editor.putString(ReadingParameters.FOOD_NAME, String.valueOf(stringBuilder));
        editor.putString(ReadingParameters.FOOD_QUANTITY, String.valueOf(stringBuilder2));
        editor.putString(ReadingParameters.FOOD_COUNT, String.valueOf(foodCount));
        editor.putString(ReadingParameters.IS_HAD_BREAKFAST,  String.valueOf(isBreakfast));
        editor.putString(ReadingParameters.IS_HAD_LUNCH, String.valueOf(isLunch));
        editor.putString(ReadingParameters.IS_HAD_DINNER, String.valueOf(isDinner));
        editor.putString(ReadingParameters.NAME, name);
        editor.putString(ReadingParameters.GENDER, gender);
        editor.putString(ReadingParameters.SMOKING_UNITS, String.valueOf(cigarettesCount));
        editor.putString(ReadingParameters.AGE, age);
        editor.putString(ReadingParameters.WEIGHT, weight);
        editor.putString(ReadingParameters.HEIGHT, height);
        editor.apply();

        TextView txtLog =findViewById(R.id.txt_log);

        txtLog.append("Water Intake: " + sharedPreferences.getString(ReadingParameters.WATER_INTAKE, "") + "\n");
        txtLog.append("Cigarettes Count: " + sharedPreferences.getString(ReadingParameters.SMOKING_UNITS, "") + "\n");
        txtLog.append("Alcohol Consumption: " + sharedPreferences.getString(ReadingParameters.IS_TAKEN_ALCOHOL, "") + "\n");
        txtLog.append("Sleep Hours: " + sharedPreferences.getString(ReadingParameters.SLEEP_HOURS, "") + "\n");
        txtLog.append("Exercise Count: " + sharedPreferences.getString(ReadingParameters.EXERCISE_IN_MINUTES, "") + "\n");
        txtLog.append("Food Name: " + sharedPreferences.getString(ReadingParameters.FOOD_NAME, "") + "\n");
        txtLog.append("Food Quantity: " + sharedPreferences.getString(ReadingParameters.FOOD_QUANTITY, "") + "\n");
        txtLog.append("Food Count: " + sharedPreferences.getString(ReadingParameters.FOOD_COUNT, "") + "\n");
        txtLog.append("Had Breakfast: " + sharedPreferences.getString(ReadingParameters.IS_HAD_BREAKFAST, "") + "\n");
        txtLog.append("Had Lunch: " + sharedPreferences.getString(ReadingParameters.IS_HAD_LUNCH, "") + "\n");
        txtLog.append("Had Dinner: " + sharedPreferences.getString(ReadingParameters.IS_HAD_DINNER, "") + "\n");
        txtLog.append("Name: " + sharedPreferences.getString(ReadingParameters.NAME, "") + "\n");
        txtLog.append("Gender: " + sharedPreferences.getString(ReadingParameters.GENDER, "") + "\n");
        txtLog.append("Age: " + sharedPreferences.getString(ReadingParameters.AGE, "") + "\n");
        txtLog.append("Height: " + sharedPreferences.getString(ReadingParameters.HEIGHT, "") + "\n");
        txtLog.append("Weight: " + sharedPreferences.getString(ReadingParameters.WEIGHT, "") + "\n");


        Intent intent = new Intent(getApplicationContext(), BeforeReading.class);
        startActivity(intent);
    }
    @SuppressLint("SetTextI18n")
    private void performWaterCounter(){
        ImageButton buttonWaterMinus =findViewById(R.id.button_water_minus);
        ImageButton buttonWaterPlus =findViewById(R.id.button_water_plus);
        TextView txtWaterCount =findViewById(R.id.txt_water_glasses);
        txtWaterCount.setText(waterCount +" glass");
        buttonWaterMinus.setOnClickListener(v -> {

            txtWaterCount.setText("");
            if (waterCount>0){
                waterCount--;
            }
            txtWaterCount.setText(waterCount +" glass");
        });

        buttonWaterPlus.setOnClickListener(v -> {
            txtWaterCount.setText("");
            waterCount++;
            txtWaterCount.setText(waterCount +" glass");
        });

    }

    private void performAlcohol(){
        RadioGroup radioGroup = findViewById(R.id.radio_group_alcohol_taken);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_button_alcohol_yes) {
                alcoholConsumption=90;

            } else if (checkedId == R.id.radio_button_alcohol_no) {
                alcoholConsumption=0;
            }
        });
    }
    private void performSmokeCounter(){
        ImageButton buttonSmokeMinus =findViewById(R.id.image_button_smoke_minus);
        ImageButton buttonSmokePlus =findViewById(R.id.image_button_smoke_plus);
        TextView txtCigarettes =findViewById(R.id.txt_cigarettes);

        txtCigarettes.setText(String.valueOf(cigarettesCount));

        buttonSmokeMinus.setOnClickListener(v -> {
            txtCigarettes.setText("");
            if (cigarettesCount>0){
                cigarettesCount--;
            }
            txtCigarettes.setText(String.valueOf(cigarettesCount));
        });

        buttonSmokePlus.setOnClickListener(v -> {
            txtCigarettes.setText("");
            cigarettesCount++;
            txtCigarettes.setText(String.valueOf(cigarettesCount));
        });

    }

    @SuppressLint("SetTextI18n")
    private void performExercise(){
        Button buttonExerciseMinus =findViewById(R.id.button_exc_minus);
        Button buttonExercisePlus =findViewById(R.id.button_exc_add);
        TextView txtExerciseCount =findViewById(R.id.txt_exercise);
        txtExerciseCount.setText(exerciseCount +" minutes");

        buttonExerciseMinus.setOnClickListener(v -> {

            txtExerciseCount.setText("");
            if (exerciseCount>0){
                exerciseCount= exerciseCount-15;
            }
            txtExerciseCount.setText(exerciseCount +" minutes");
        });

        buttonExercisePlus.setOnClickListener(v -> {
            txtExerciseCount.setText("");
            exerciseCount=exerciseCount+15 ;
            txtExerciseCount.setText(exerciseCount +" minutes");
        });

    }
    private void performMeal(){

        CheckBox chkBreakfast=findViewById(R.id.chk_breakfast);
        CheckBox chkLunch=findViewById(R.id.chk_lunch);
        CheckBox chkDinner=findViewById(R.id.chk_dinner);


        chkBreakfast.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Checkbox is checked
            // Checkbox is unchecked
            isBreakfast= !isChecked;
        });

        chkLunch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Checkbox is checked
            // Checkbox is unchecked
            isLunch= !isChecked;
        });

        chkDinner.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Checkbox is checked
            // Checkbox is unchecked
            isDinner= !isChecked;
        });


    }

    private void performAddedFood(){

        RecyclerView recyclerView4 = findViewById(R.id.list_added_food_items);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));

        AddedFoodAdapter2 addedFoodAdapter;
        List<AddedFoodData> foodList;
        FoodDataBase database;
        foodList = new ArrayList<>();

        // Initialize the FoodDataBase
        database = new FoodDataBase(getApplicationContext());

        foodList = database.getAllFood();



        // Create and set the adapter
        addedFoodAdapter = new AddedFoodAdapter2(foodList, getApplicationContext());
        recyclerView4.setAdapter(addedFoodAdapter);


        LinearLayout emptyListMessage =findViewById(R.id.empty_list_message);
        int dataCount = database.getDataCount();



        for (AddedFoodData foodData : foodList) {
            String foodName = foodData.getFoodName();
            String foodQuantity = foodData.getFoodQuantity();
            foodItems.put(foodName, foodQuantity);
        }

        int index=0;
        foodCount=foodItems.size();



        for (Map.Entry<String, String> entry : foodItems.entrySet()) {
            String foodName = entry.getKey().trim();
            String quantity = entry.getValue().trim();

            stringBuilder.append("food_name_").append(index).append("=").append(foodName).append("&");
            stringBuilder2.append("food_quantity_").append(index).append("=").append(quantity).append("&");

            index++;
        }

        if (dataCount == 0){
            emptyListMessage.setVisibility(View.VISIBLE);
            recyclerView4.setVisibility(View.GONE);
        }else{
            emptyListMessage.setVisibility(View.GONE);
            recyclerView4.setVisibility(View.VISIBLE);
        }

    }



    private void fetchProfileData(){
        SharedPreferences personalData = getSharedPreferences(ReadingParameters.TITLE, Context.MODE_PRIVATE);
        decodePersonalData(personalData.getString(ReadingParameters.DATA_PERSONAL_INFORMATION, ""));
    }

    private void decodePersonalData(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            name = jsonObject.getString("name");
            gender = jsonObject.getString("gender");
            age = jsonObject.getString("age");
            height = jsonObject.getString("height");
            weight = jsonObject.getString("weight");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fetchFriendData(){

    }
}