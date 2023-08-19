package com.humorstech.respyr.home;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;

public class RoundedBarChartRenderer extends BarChartRenderer {

    private Canvas c;
    private IBarDataSet dataSet;
    private int index;

    public RoundedBarChartRenderer(BarChart chart, com.github.mikephil.charting.animation.ChartAnimator animator, com.github.mikephil.charting.utils.ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        Transformer transformer = mChart.getTransformer(dataSet.getAxisDependency());

        mShadowPaint.setColor(dataSet.getBarShadowColor());
        mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));

        // Calculate bar spacing
        float barSpace = 0.03f; // Adjust this value for the desired spacing between bars

        // Set bar color
        int barColor = Color.parseColor("#308BF9");
        // Draw bars with rounded corners
        for (int i = 0; i < dataSet.getEntryCount(); i++) {
            BarEntry entry = dataSet.getEntryForIndex(i);
            float x = entry.getX();
            float y = entry.getY();

            float xPos = x + index * (mChart.getBarData().getBarWidth() ) - mChart.getBarData().getBarWidth() / 2f;

            float[] transformed = new float[]{
                    xPos, y, // top-left
                    xPos + mChart.getBarData().getBarWidth(), y, // top-right
                    xPos + mChart.getBarData().getBarWidth(), 0, // bottom-right
                    xPos, 0 // bottom-left
            };

            transformer.pointValuesToPixel(transformed);

            // Apply rounded corners
            float cornerRadius = 15f;
            Path path = new Path();
            path.addRoundRect(new RectF(transformed[0], transformed[1], transformed[4], transformed[7]), cornerRadius, cornerRadius, Path.Direction.CW);

            // Set bar color
            mRenderPaint.setColor(barColor);
            mRenderPaint.setStrokeWidth(0f);
            c.drawPath(path, mRenderPaint);
            if (mBarBorderPaint.getStrokeWidth() > 0f) {
                c.drawPath(path, mBarBorderPaint);
            }
        }
    }




}
