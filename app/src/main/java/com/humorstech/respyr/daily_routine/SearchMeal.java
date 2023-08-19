package com.humorstech.respyr.daily_routine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.daily_routine.added.AddedFoodAdapter;
import com.humorstech.respyr.daily_routine.added.AddedFoodData;
import com.humorstech.respyr.daily_routine.search.FrequentlyFoodAdapter;
import com.humorstech.respyr.daily_routine.search.FrequentlyFoodData;
import com.humorstech.respyr.daily_routine.search.FoodDataBase;
import com.humorstech.respyr.daily_routine.search.SearchFoodData;
import com.humorstech.respyr.daily_routine.search.SearchFoodDataAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchMeal extends AppCompatActivity{

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private SearchFoodDataAdapter foodAdapter;
    private List<SearchFoodData> foodList;
    private List<SearchFoodData> filteredFoodList;
    private LinearLayout searchedFoodItemLayout;
    private TextView txtSearchedFoodNotFound;
    private ImageButton buttonClearEditText;

    private LinearLayout llFoodNotFoundLayout;
    private TextView txtNotFoundFoodName;

    private ImageButton buttonBack;

    private int id;
    private String name;
    private String gender;
    private String age;
    private String height;
    private String weight;
    private String waterIntake;
    private String alcohol;
    private String smoking;
    private String exercise;
    private String exerciseTime;
    private String meals;
    private String dttm;

    public SearchMeal(){
        id=0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meal);
        StatusBarColor statusBarColor = new StatusBarColor(SearchMeal.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));



        Dialogs.showLoadingDialog(this,"Wait....");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialogs.hideLoadingDialog();
                setVars();
                searchEditText.setText("");
                init();
                performFrequentlyAddedFood();
                performAddedFood();
            }
        },500);

    }


    private void init() {

        fetchFoodItemsFromServer();
        onClicks();


        foodList = new ArrayList<>();
        filteredFoodList = new ArrayList<>();
        foodAdapter = new SearchFoodDataAdapter(filteredFoodList, getApplicationContext(), SearchMeal.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchMeal.this));
        recyclerView.setAdapter(foodAdapter);


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString().toLowerCase().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (searchEditText.getText().toString().isEmpty()){
                    searchedFoodItemLayout.setVisibility(View.GONE);

                    buttonClearEditText.setVisibility(View.GONE);
                }else{
                    buttonClearEditText.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonClearEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
            }
        });



    }

    private void onClicks(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setVars() {
        searchEditText = findViewById(R.id.search_food_edit_text);
        recyclerView = findViewById(R.id.list_search_food_items);
        searchedFoodItemLayout = findViewById(R.id.searched_food_item_layout);
        searchedFoodItemLayout.setVisibility(View.GONE);
        txtSearchedFoodNotFound = findViewById(R.id.txt_food_not_found);
        buttonClearEditText = findViewById(R.id.button_clear_edit_text);
        llFoodNotFoundLayout = findViewById(R.id.ll_food_not_found);
        txtNotFoundFoodName = findViewById(R.id.txt_not_found_food_name);
        buttonBack = findViewById(R.id.button_back);

        searchEditText.setText("");
    }

    private void fetchFoodItemsFromServer() {
        String url = "https://humorstech.com/humors_app/app_final/food/fetch_food.php"; // Replace with your server URL

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


                if (foodList != null) {
                    List<SearchFoodData> foodItems = parseFoodItemsFromJson(response);

                    if (foodItems != null) {
                        foodList.clear(); // Clear the existing list
                        foodList.addAll(foodItems);
                        foodAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("FoodItems", "Parsing JSON failed or resulted in null foodItems");
                    }
                } else {
                    Log.e("FoodList", "foodList is null");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(SearchMeal.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<SearchFoodData> parseFoodItemsFromJson(JSONArray jsonArray) {
        List<SearchFoodData> foodItems = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                String foodName = jsonObject.getString("food_name");
                String foodCategory = jsonObject.getString("food_category");
                String imageLink = jsonObject.getString("image_link");

                String imagePath = "https://humorstech.com/humors_app/app_final/";
                imageLink = imagePath + imageLink;

                SearchFoodData foodItem = new SearchFoodData(id, foodName, foodCategory, imageLink);
                foodItems.add(foodItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return foodItems;
    }

    private void performSearch(String query) {
        filteredFoodList.clear(); // Clear the existing filtered list

        // Iterate through the foodList and add items that match the search query
        for (SearchFoodData foodItem : foodList) {
            String foodName = foodItem.getFoodName().toLowerCase();

            if (foodName.startsWith(query)) {
                filteredFoodList.add(foodItem);
            }
        }

        foodAdapter.notifyDataSetChanged(); // Notify the adapter of the data change

        // Show or hide the RecyclerView based on the filteredFoodList size
        if (filteredFoodList.isEmpty()) {
            searchedFoodItemLayout.setVisibility(View.GONE); // Hide the RecyclerView
            llFoodNotFoundLayout.setVisibility(View.VISIBLE); // Show the "Not found" TextView
        } else {
            searchedFoodItemLayout.setVisibility(View.VISIBLE); // Show the RecyclerView
            llFoodNotFoundLayout.setVisibility(View.GONE); // Hide the "Not found" TextView
        }
    }

    private void performFrequentlyAddedFood(){
        // Step 1: Create the item list
        List<FrequentlyFoodData> itemList2 = new ArrayList<>();
        itemList2.add(new FrequentlyFoodData(1, "Rice", "Grains", "100 g", ""));
        itemList2.add(new FrequentlyFoodData(31, "Dal Mixed", "Dal", "100 g", ""));
        itemList2.add(new FrequentlyFoodData(63, "Roti", "Breads", "100 g", ""));
        itemList2.add(new FrequentlyFoodData(36, "Idli", "Breakfast", "100 g", "https://humorstech.com/humors_app/app_final/food_images/36.png"));

        // Step 2: Create the ItemAdapter
        FrequentlyFoodAdapter adapter = new FrequentlyFoodAdapter(SearchMeal.this, itemList2);

        // Step 3: Set the adapter on the RecyclerView
        RecyclerView recyclerView2 = findViewById(R.id.list_frequently_added_food_items);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext())); // Set the layout manager
        recyclerView2.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void performAddedFood(){

        RecyclerView recyclerView4 = findViewById(R.id.list_added_food_items1);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));

        AddedFoodAdapter addedFoodAdapter;
        List<AddedFoodData> foodList;
        FoodDataBase database;
        foodList = new ArrayList<>();

        // Initialize the FoodDataBase
        database = new FoodDataBase(getApplicationContext());

        foodList = database.getAllFood();


        // Create and set the adapter
        addedFoodAdapter = new AddedFoodAdapter(foodList, getApplicationContext());
        recyclerView4.setAdapter(addedFoodAdapter);



        int dataCount = database.getDataCount();


        LinearLayout addedFoodLayout =findViewById(R.id.added_food_item_layout);
        LinearLayout buttonNext =findViewById(R.id.button_next);



        TextView foodDataCount =findViewById(R.id.txt_data_count);
        TextView txtSelectedMeals =findViewById(R.id.txt_selected_meals);

        if (dataCount >=1){
            addedFoodLayout.setVisibility(View.VISIBLE);
            buttonNext.setVisibility(View.VISIBLE);
            foodDataCount.setText("Add Meal ("+dataCount+")");
            txtSelectedMeals.setText("Selected ("+dataCount+")");
        }else{
            addedFoodLayout.setVisibility(View.GONE);
            buttonNext.setVisibility(View.GONE);
        }

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onBackPressed();
            }
        });

    }



}
