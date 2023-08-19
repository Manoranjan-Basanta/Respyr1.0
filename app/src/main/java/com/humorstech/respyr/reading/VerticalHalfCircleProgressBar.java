package com.humorstech.respyr.reading;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class VerticalHalfCircleProgressBar extends View {

    private int progress;
    private Paint paint;
    private RectF arcRect;

    public VerticalHalfCircleProgressBar(Context context) {
        super(context);
        init();
    }

    public VerticalHalfCircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);

        arcRect = new RectF();
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate(); // Request a redraw
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidth = getWidth();
        int viewHeight = getHeight();

        int centerX = viewWidth / 2;
        int centerY = viewHeight;

        int radius = Math.min(centerX, centerY);

        // Set the bounds of the half-circle
        arcRect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        // Draw the background
        paint.setColor(Color.GRAY);
        canvas.drawRect(centerX - radius, centerY - (2 * radius), centerX + radius, centerY, paint);

        // Calculate the progress height
        float progressHeight = (float) progress / 100 * (2 * radius);

        // Draw the progress
        paint.setColor(Color.GREEN);
        canvas.drawRect(centerX - radius, centerY - progressHeight, centerX + radius, centerY, paint);
    }
}
