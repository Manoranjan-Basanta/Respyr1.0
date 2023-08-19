package com.humorstech.respyr.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.humorstech.respyr.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {
    private BarChart barChart;

    private  List<String> labels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        barChart = findViewById(R.id.barChart);




        barchart();

        fetchBMIScore();

    }
    private void barchart(){

        BarChart barChart = findViewById(R.id.barChart);


        ArrayList<BarEntry> entries = fetchData();
        //data set--------------------------------------------------->
        BarDataSet dataSet = new BarDataSet(entries, "Data Set");

        //data---------------------------------------------------->
        BarData barData = new BarData(dataSet);

        // set column width--------------------------------------->
        barData.setBarWidth(0.55f);
        // Hide value above bars---------------------------------->
        barData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ""; // Return an empty string to hide the value
            }
        });


        //chart---------------------------------------------------->
        // Hide legend
        barChart.getLegend().setEnabled(false);
        // Hide description label
        barChart.getDescription().setEnabled(false);



        RoundedBarChartRenderer roundedBarChartRenderer = new RoundedBarChartRenderer(barChart, barChart.getAnimator(), barChart.getViewPortHandler());
        barChart.setRenderer(roundedBarChartRenderer);





        // Customize the axis lines and labels
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setDrawAxisLine(true);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawLabels(true);

        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawLabels(false);

        barChart.getAxisRight().setEnabled(false);



        //
        // Set week days as labels
        List<String> weekDays = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(weekDays));


        // Set custom font family
        Typeface customTypeface = ResourcesCompat.getFont(getApplicationContext(), R.font.roboto_medium);

        int axisLineColor = Color.parseColor("#E6E6E6");
        int labelColor = Color.parseColor("#535359");


        barChart.getXAxis().setAxisLineWidth(2f);
        barChart.getXAxis().setAxisLineColor(axisLineColor);

        // Customize the components with the custom font
        barChart.getXAxis().setTypeface(customTypeface);

        // set label color
        barChart.getXAxis().setTextColor(labelColor);
        barChart.getXAxis().setTextSize(8f);

        barChart.getAxisLeft().setTypeface(customTypeface);
        barChart.getAxisRight().setTypeface(customTypeface);
        barChart.getDescription().setTypeface(customTypeface);


        barChart.setData(barData);
        barChart.invalidate();

    }
    private ArrayList<BarEntry> fetchData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 21f));
        entries.add(new BarEntry(1f, 20f));
        entries.add(new BarEntry(2f, 24f));
        entries.add(new BarEntry(3f, 19f));
        entries.add(new BarEntry(4f, 21f));
        entries.add(new BarEntry(5f, 19f));
        entries.add(new BarEntry(6f, 23f));
        return entries;
    }

    private void fetchBMIScore(){
        String jsonString = "{\"id\":20,\"data\":{\"monday\":25,\"tuesday\":25,\"wednesday\":25,\"thursday\":25,\"friday\":25,\"saturday\":25,\"sunday\":25}}";
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            int id = jsonObject.getInt("id");

            JSONObject dataObject = jsonObject.getJSONObject("data");
            int mondayValue = dataObject.getInt("monday");
            int tuesdayValue = dataObject.getInt("tuesday");
            int wednesdayValue = dataObject.getInt("wednesday");
            int thursdayValue = dataObject.getInt("thursday");
            int fridayValue = dataObject.getInt("friday");
            int saturdayValue = dataObject.getInt("saturday");
            int sundayValue = dataObject.getInt("sunday");

            System.out.println("ID: " + id);
            System.out.println("Monday: " + mondayValue);
            System.out.println("Tuesday: " + tuesdayValue);
            System.out.println("Wednesday: " + wednesdayValue);
            System.out.println("Thursday: " + thursdayValue);
            System.out.println("Friday: " + fridayValue);
            System.out.println("Saturday: " + saturdayValue);
            System.out.println("Sunday: " + sundayValue);



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}