package com.humorstech.respyr.reading;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.humorstech.respyr.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomMarkerView extends MarkerView {

    private TextView tvContent;
    private SimpleDateFormat dateFormat;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = findViewById(R.id.tvContent);
        dateFormat = new SimpleDateFormat("dd MMM\nEEE", Locale.getDefault());
    }

    // Callbacks every time the MarkerView is redrawn
    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
        // Set the value to be displayed
        tvContent.setText(String.valueOf(entry.getY()));

        // Convert the x-value (index) to a Date
        int index = (int) entry.getX();
        LineChart chart = (LineChart) getChartView();
        Date date = getDateFromIndex(chart, index);

        // Set the formatted date as the label
        tvContent.setContentDescription(dateFormat.format(date));

        super.refreshContent(entry, highlight);
    }

    // Offset the MarkerView relative to the position where it is drawn
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2f), -getHeight());
    }

    private Date getDateFromIndex(LineChart chart, int index) {
        // Assuming your chart's X-axis data is a List of Date objects
        List<Date> dates = getDatesFromXAxisData(chart);
        if (index >= 0 && index < dates.size()) {
            return dates.get(index);
        }
        return null;
    }

    private List<Date> getDatesFromXAxisData(LineChart chart) {
        List<Date> dates = new ArrayList<>();
        // Add your logic here to extract the dates from your X-axis data
        // and populate the 'dates' list accordingly
        return dates;
    }
}
