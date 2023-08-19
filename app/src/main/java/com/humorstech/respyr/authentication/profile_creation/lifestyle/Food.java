package com.humorstech.respyr.authentication.profile_creation.lifestyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.daily_routine.SearchMeal;
import com.humorstech.respyr.daily_routine.added.AddedFoodAdapter2;
import com.humorstech.respyr.daily_routine.added.AddedFoodData;
import com.humorstech.respyr.daily_routine.search.FoodDataBase;
import com.humorstech.respyr.utills.ProfileCreationData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Food extends AppCompatActivity {

    HashMap<String, String> foodItems = new HashMap<>();
    private final StringBuilder stringBuilder = new StringBuilder();
    private final StringBuilder stringBuilder2 = new StringBuilder();
    private int foodCount=0;

    private Button buttonNext;
    private Button buttonBack;


    private boolean isBreakfast=true;
    private boolean isLunch=true;
    private boolean isDinner=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        StatusBarColor statusBarColor = new StatusBarColor(Food.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));
        FoodDataBase dataBase=new FoodDataBase(getApplicationContext());
        dataBase.clearTable();

        init();
    }
    private void init(){
        setReferences();
        onClicks();
        performMeal();
    }

    @Override
    protected void onStart() {
        super.onStart();
        performAddedFood();
    }

    private void setReferences(){

        buttonNext =findViewById(R.id.button_next);
        buttonBack =findViewById(R.id.button_back);



        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 5,3);
    }
    private void onClicks(){
        Button buttonAddMeal =findViewById(R.id.button_add_meal);
        buttonAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Food.this, SearchMeal.class);
                startActivity(intent);
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {



                    // store gender id for profile creation
                    SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ProfileCreationData.FOOD_INTAKE, String.valueOf(foodCount));
                    editor.putString(ProfileCreationData.FOOD_NAME, String.valueOf(stringBuilder));
                    editor.putString(ProfileCreationData.FOOD_QUANTITY, String.valueOf(stringBuilder2));
                    editor.putString(ProfileCreationData.BREAKFAST, String.valueOf(isBreakfast));
                    editor.putString(ProfileCreationData.LUNCH, String.valueOf(isDinner));
                    editor.putString(ProfileCreationData.DINNER, String.valueOf(isLunch));
                    editor.apply();


                    Intent intent = new Intent(Food.this, SleepHours.class);
                    startActivity(intent);
                }catch (ActivityNotFoundException e){

                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void performAddedFood(){

        RecyclerView recyclerView4 = findViewById(R.id.list_added_food_items);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));

        AddedFoodAdapter2 addedFoodAdapter;
        List<AddedFoodData> foodList;
        FoodDataBase database;

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

            stringBuilder.append("food_name_"+ index +"="+foodName+"&");
            stringBuilder2.append("food_quantity_"+ index +"="+quantity+"&");

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

    private void performMeal(){
        CheckBox chkBreakfast=findViewById(R.id.chk_breakfast);
        CheckBox chkLunch=findViewById(R.id.chk_lunch);;
        CheckBox chkDinner=findViewById(R.id.chk_dinner);


        chkBreakfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Checkbox is checked
                    isBreakfast=false;
                } else {
                    // Checkbox is unchecked
                    isBreakfast=true;
                }
            }
        });

        chkLunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Checkbox is checked
                    isLunch=false;
                } else {
                    // Checkbox is unchecked
                    isLunch=true;
                }
            }
        });

        chkDinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Checkbox is checked
                    isDinner=false;
                } else {
                    // Checkbox is unchecked
                    isDinner=true;
                }
            }
        });


    }
}