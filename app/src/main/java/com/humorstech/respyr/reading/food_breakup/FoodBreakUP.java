package com.humorstech.respyr.reading.food_breakup;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.daily_routine.added.AddedFoodAdapter2;
import com.humorstech.respyr.daily_routine.added.AddedFoodData;
import com.humorstech.respyr.daily_routine.search.FoodDataBase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class FoodBreakUP extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<FoodBreakupData> foodItemList;

    StringBuilder foodNames = new StringBuilder();
    StringBuilder foodQuanity = new StringBuilder();


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_break_up);

        performAddedFood();
        Dialogs.showLoadingDialog(FoodBreakUP.this, "Please wait");
        fetchFoodData();

        StatusBarColor statusBarColor= new StatusBarColor(FoodBreakUP.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        ImageButton buttonBack=findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void performAddedFood(){

        HashMap<String, String> foodItems = new HashMap<>();

        AddedFoodAdapter2 addedFoodAdapter;
        List<AddedFoodData> foodList;
        FoodDataBase database;
        foodList = new ArrayList<>();

        database = new FoodDataBase(getApplicationContext());

        foodList = database.getAllFood();


        for (AddedFoodData foodData : foodList) {
            String foodName = foodData.getFoodName();
            String foodQuantity = foodData.getFoodQuantity();
            foodItems.put(foodName, foodQuantity);
        }

        for (Map.Entry<String, String> entry : foodItems.entrySet()) {
            String foodName = entry.getKey();
            String quantity = entry.getValue();

            foodNames.append(foodName+",");
            foodQuanity.append(quantity+",");
        }
    }


    private void fetchFoodData(){


        RequestParams params = new RequestParams();
        params.put("foodNames", String.valueOf(foodNames));
        params.put("foodAmounts", String.valueOf(foodQuanity));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://humorstech.com/humors_app/app_final/food/fetch_food_data_by_name.php?", params
                , new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                System.out.println("Response"+params);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);

                System.out.println("Response"+response);

                foodItemList = parseJsonData(response);
                // Call the setupTabLayout method
                setupTabLayout();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Dialogs.hideLoadingDialog();
            }

        });
    }

    private List<FoodBreakupData> parseJsonData(String jsonData) {
        List<FoodBreakupData> foodItemList = new ArrayList<>();

        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String food = jsonObject.getString("food");
                String image = jsonObject.getString("image");
                String image2 = jsonObject.getString("image2");
                String calories = jsonObject.getString("calories");
                String carbohydrates = jsonObject.getString("carbohydrates");
                String protein = jsonObject.getString("protein");
                String fat = jsonObject.getString("fat");
                String fiber = jsonObject.getString("fiber");
                String foodAmount = jsonObject.getString("amount");
                String foodCategory = jsonObject.getString("category");
                int id = jsonObject.getInt("id");

                if (!food.equals("")) {

                    System.out.println("-------------------------------");
                    System.out.println("Food: " + food);
                    System.out.println("Calories: " + calories);
                    System.out.println("Carbohydrates: " + carbohydrates);
                    System.out.println("Protein: " + protein);
                    System.out.println("Fat: " + fat);
                    System.out.println("Fiber: " + fiber);
                    System.out.println();



                    image = "https://humorstech.com/humors_app/app_final/food_images/" +image;

                    FoodBreakupData foodItem = new FoodBreakupData(id, food,foodAmount, foodCategory,image, calories,carbohydrates,protein,fat,fiber );
                    foodItemList.add(foodItem);
                }


//                String category = jsonObject.getString("Category");
//                String calories = jsonObject.getString("Calories (kcal)");
//                String foodImageLink = jsonObject.getString("Image_sm");
//                String foodCarboHydrate = jsonObject.getString("Carbohydrates (g)");
//                String foodProtein = jsonObject.getString("Protein (g)");
//                String foodFats = jsonObject.getString("Fat (g)");
//                String foodFiber = jsonObject.getString("Fiber (g)");


//                calories = calories + " Kcal";
//
//
//
//
//                    // Create a FoodBreakupData object and add it to the list



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return foodItemList;
    }





    private void setupTabLayout() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Create an instance of the adapter
        FoodBreakupPageAdapter adapter = new FoodBreakupPageAdapter(getSupportFragmentManager(), foodItemList);

        // Set the adapter to the ViewPager
        viewPager.setAdapter(adapter);

        // Connect the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // Set tab titles
        Dialogs.hideLoadingDialog();


        tabLayout.getTabAt(0).setText("Calories");
        tabLayout.getTabAt(1).setText("Carbohydrate");
        tabLayout.getTabAt(2).setText("Protein");
        tabLayout.getTabAt(3).setText("Fats");
        tabLayout.getTabAt(4).setText("Fibre");

        // Hide the tab indicator line
        tabLayout.setSelectedTabIndicator(null);

        // Set the default selected tab
        TabLayout.Tab defaultTab = tabLayout.getTabAt(0); // Set Tab 1 as the default selected tab
        defaultTab.select();

        // Set selected tab text color
        int selectedTextColor = getResources().getColor(R.color.white); // Replace R.color.selectedTabColor with your desired color resource
        int defaultTextColor = getResources().getColor(R.color.grey); // Replace R.color.defaultTabColor with your desired color resource
        tabLayout.setTabTextColors(defaultTextColor, selectedTextColor);

        // Set background color for index 0 tab
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.view.setBackgroundColor(ContextCompat.getColor(FoodBreakUP.this, R.color.primary)); // Replace R.color.primary with your desired color resource for the index 0 tab background

        // Set selected tab background color
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(ContextCompat.getColor(FoodBreakUP.this, R.color.primary)); // Replace R.color.primary with your desired color resource for the selected tab background
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(Color.TRANSPARENT); // Reset background color for unselected tabs
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
