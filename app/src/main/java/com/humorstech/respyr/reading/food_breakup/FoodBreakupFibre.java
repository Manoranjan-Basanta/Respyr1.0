package com.humorstech.respyr.reading.food_breakup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.humorstech.respyr.R;

import java.util.List;


public class FoodBreakupFibre extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<FoodBreakupData> foodItemList;
    public FoodBreakupFibre(List<FoodBreakupData> foodItemList) {
        // Required empty public constructor
        this.foodItemList=foodItemList;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView recyclerView;
    private FoodBreakupAdapter adapter;

    private static final String TAG = "FoodBreakupCalories";
    private TextView txtFoodBreakupActualCalories, txtFoodBreakupRecommendedCalories, txtFoodBreakupDifference;
    private ImageView imgFoodBreakupCat;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_breakup_fibre, container, false);

        retriveData();
        recyclerView =view.findViewById(R.id.food_breakup_list);

        adapter = new FoodBreakupAdapter(foodItemList, getContext(), 5);

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        recyclerView = view.findViewById(R.id.food_breakup_list);
        txtFoodBreakupActualCalories = view.findViewById(R.id.txt_food_breakup_actual);
        txtFoodBreakupRecommendedCalories = view.findViewById(R.id.txt_food_breakup_recommended);
        txtFoodBreakupDifference = view.findViewById(R.id.txt_food_breakup_difference);
        imgFoodBreakupCat = view.findViewById(R.id.img_food_breakup_cat);

        caloriesAnalysis();


        return view;
    }

    @SuppressLint("SetTextI18n")
    private void caloriesAnalysis(){

        double actualCalories =curr_fib;
        double recommendedCalories =reco_fib;
        double differenceCalories =recommendedCalories-actualCalories;



        String differenceMessage;
        if (differenceCalories<0){
            int positiveI = (int)Math.abs(differenceCalories);

            String htmlString = "<b>"+positiveI+" g</b>";
            Spanned spannedText = Html.fromHtml(htmlString);
            differenceMessage= name+","+"\nYour food intake exceeds "+spannedText;


        }else if(differenceCalories==0){
            String htmlString = (int)recommendedCalories+" <b> g</b>";
            Spanned spannedText = Html.fromHtml(htmlString);
            differenceMessage= name+","+"\nYour food intake matches the recommended calorie intake of "+spannedText;
        }
        else{
            String htmlString = (int)differenceCalories+" <b> g</b>";
            Spanned spannedText = Html.fromHtml(htmlString);
            differenceMessage= name+","+"\nYour food intake lacks "+spannedText;
        }

        imgFoodBreakupCat.setImageResource(R.drawable.fibre_dark);
        txtFoodBreakupActualCalories.setText(String.valueOf((int)actualCalories)+" g");
        txtFoodBreakupRecommendedCalories.setText("/"+String.valueOf((int)recommendedCalories)+" g");
        txtFoodBreakupDifference.setText(differenceMessage);
    }

    private void retriveData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("result_data", Context.MODE_PRIVATE);
        String curr_calStr = sharedPreferences.getString("curr_cal", "");
        String curr_carStr = sharedPreferences.getString("curr_car", "");
        String curr_proStr = sharedPreferences.getString("curr_pro", "");
        String curr_fatStr = sharedPreferences.getString("curr_fat", "");
        String curr_fibStr = sharedPreferences.getString("curr_fib", "");
        String reco_calStr = sharedPreferences.getString("reco_cal", "");
        String reco_carStr = sharedPreferences.getString("reco_car", "");
        String reco_proStr = sharedPreferences.getString("reco_pro", "");
        String reco_fatStr = sharedPreferences.getString("reco_fat", "");
        String reco_fibStr = sharedPreferences.getString("reco_fib", "");
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


    }

}