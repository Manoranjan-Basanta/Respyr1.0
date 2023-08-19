package com.m.sleeproutinchart;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barChart = findViewById(R.id.barChart);
        setupBarChart();
        setData();


    }

    private void setupBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void setData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 3));
        entries.add(new BarEntry(2, 2));
        entries.add(new BarEntry(3, 5));
        entries.add(new BarEntry(4, 4));
        entries.add(new BarEntry(5, 4));
        entries.add(new BarEntry(6, 4));
        entries.add(new BarEntry(7, 4));

        BarDataSet dataSet = new BarDataSet(entries, "Values");

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(10f);

        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        RoundedBarChart roundedBarChart = new RoundedBarChart(barChart, barChart.getAnimator(), barChart.getViewPortHandler());
        barChart.setRenderer(roundedBarChart);

        barChart.setData(data);
        barChart.invalidate();
    }
}


