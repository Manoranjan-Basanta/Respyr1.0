package com.humorstech.respyr.home;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CustomBarRenderer extends BarChartRenderer {
    private Paint backgroundPaint;
    private float cornerRadius; // Specify the corner radius value

    public CustomBarRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.LTGRAY);
        cornerRadius = 20f; // Set the desired corner radius value

    }

    @Override
    public void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        if (!dataSet.isVisible() || dataSet.getEntryCount() == 0)
            return;

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mShadowPaint.setColor(dataSet.getBarShadowColor());

        float phaseX = Math.max(0.f, Math.min(1.f, mAnimator.getPhaseX()));
        float phaseY = Math.max(0.f, Math.min(1.f, mAnimator.getPhaseY()));

        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);

        buffer.setBarWidth(mChart.getBarData().getBarWidth());

        buffer.feed(dataSet);

        trans.pointValuesToPixel(buffer.buffer);

        boolean isSingleColor = dataSet.getColors().size() == 1;



        for (int j = 0; j < buffer.size(); j += 4) {

            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                continue;

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                break;

            if (isSingleColor)
                mRenderPaint.setColor(dataSet.getColor());
            else
                mRenderPaint.setColor(dataSet.getColor(j / 4));

            if (dataSet.getBarBorderWidth() > 0f) {
                c.drawRect(buffer.buffer[j], mViewPortHandler.contentTop(),
                        buffer.buffer[j + 2], mViewPortHandler.contentBottom(), backgroundPaint);
                c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3], mRenderPaint);
            } else {
                c.drawRect(buffer.buffer[j], mViewPortHandler.contentTop(),
                        buffer.buffer[j + 2], mViewPortHandler.contentBottom(), backgroundPaint);
                c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3], mRenderPaint);
            }



            if (dataSet.getBarBorderWidth() > 0f) {
                // Draw the bar with rounded corners
                Path barPath = new Path();
                barPath.addRoundRect(new RectF(buffer.buffer[j], buffer.buffer[j + 1],
                                buffer.buffer[j + 2], buffer.buffer[j + 3]),
                        cornerRadius, cornerRadius, Path.Direction.CW);
                c.drawPath(barPath, mRenderPaint);
            } else {
                // Draw the bar with rounded corners
                Path barPath = new Path();
                barPath.addRoundRect(new RectF(buffer.buffer[j], buffer.buffer[j + 1],
                                buffer.buffer[j + 2], buffer.buffer[j + 3]),
                        cornerRadius, cornerRadius, Path.Direction.CW);
                c.drawPath(barPath, mRenderPaint);
            }
        }

        for (int j = 0; j < buffer.size(); j += 4) {
            // Existing code...

            if (dataSet.getBarBorderWidth() > 0f) {
                // Draw the background with rounded corners
                drawRoundedBackground(c, buffer.buffer[j], mViewPortHandler.contentTop(),
                        buffer.buffer[j + 2], mViewPortHandler.contentBottom());

                // Draw the bar with rounded corners
                drawRoundedBar(c, buffer.buffer[j], buffer.buffer[j + 1],
                        buffer.buffer[j + 2], buffer.buffer[j + 3]);
            } else {
                // Draw the background with rounded corners
                drawRoundedBackground(c, buffer.buffer[j], mViewPortHandler.contentTop(),
                        buffer.buffer[j + 2], mViewPortHandler.contentBottom());

                // Draw the bar with rounded corners
                drawRoundedBar(c, buffer.buffer[j], buffer.buffer[j + 1],
                        buffer.buffer[j + 2], buffer.buffer[j + 3]);
            }
        }
    }
    private void drawRoundedBackground(Canvas canvas, float left, float top, float right, float bottom) {
        float[] radii = {cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f, 0f, 0f};
        Path backgroundPath = new Path();
        backgroundPath.addRoundRect(new RectF(left, top, right, bottom),
                cornerRadius, cornerRadius, Path.Direction.CW);
        canvas.drawPath(backgroundPath, backgroundPaint);
    }
    private void drawRoundedBar(Canvas canvas, float left, float top, float right, float bottom) {
        Path barPath = new Path();
        barPath.addRoundRect(new RectF(left, top, right, bottom),
                cornerRadius, cornerRadius, Path.Direction.CW);
        canvas.drawPath(barPath, mRenderPaint);
    }

}
